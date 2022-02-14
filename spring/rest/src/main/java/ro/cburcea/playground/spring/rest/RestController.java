package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        users.add(new User(1, "firstName1", "lastName1", 34, Arrays.asList("Football", "Tennis")));
        users.add(new User(2, "firstName2", "lastName2", 43, Arrays.asList("Football", "Basketball")));
        users.add(new User(3, "firstName3", "lastName3", 35, Arrays.asList("Football", "Hiking")));
    }

    // built-in support for HEAD and OPTIONS for GET methods
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();
        return ResponseEntity.of(user); //200 or 404
    }

    @PostMapping("/users")
    public ResponseEntity<Void> addUser(@RequestBody User newUser) {
        Optional<User> user = users
                .stream()
                .filter(u -> newUser.getId() == u.getId())
                .findFirst();

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        users.add(newUser);
        return ResponseEntity
                .created(URI.create("/users/" + newUser.getId()))
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        users.remove(user.get());
        return ResponseEntity.noContent().build();
    }

    // PUT replaces a resource entirely
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User userDto, @PathVariable int id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();

        if (user.isEmpty()) {
            users.add(userDto);
            return ResponseEntity
                    .created(URI.create("/users/" + userDto.getId()))
                    .build();
        }
        user.get().setFirstName(userDto.getFirstName());
        user.get().setLastName(userDto.getLastName());
        user.get().setAge(userDto.getAge());
        return ResponseEntity.noContent().build();
    }

    // partial update
    @PatchMapping("/users/{id}")
    public ResponseEntity<Void> patchUserMethod1(@RequestBody User patchedUser, @PathVariable int id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (StringUtils.hasLength(patchedUser.getFirstName())) {
            user.get().setFirstName(patchedUser.getFirstName());
        }
        if (StringUtils.hasLength(patchedUser.getLastName())) {
            user.get().setLastName(patchedUser.getLastName());
        }
        if (patchedUser.getAge() > 0) {
            user.get().setAge(patchedUser.getAge());
        }
        return ResponseEntity.noContent().build();
    }

    /*
        PATCH method
        Content-Type = application/json-patch+json
        [
            {
            "op":"test",
            "path":"/age",
            "value": 43
            },
            {
            "op":"replace",
            "path":"/age",
            "value":77
            },
            {
            "op":"add",
            "path":"/sports/0",
            "value":"Handball"
            },
            {
            "op":"remove",
            "path":"/sports/1"
            },
            {
            "op":"move",
            "from":"/sports/0",
            "path":"/sports/-"
            },
            {
            "op":"copy",
            "from":"/sports/0",
            "path":"/sports/-"
            }
        ]
     */

    // partial update
    @PatchMapping(value = "/users/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Void> patchUserMethod2(@RequestBody JsonPatch patch, @PathVariable int id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            User userPatched = UserUtils.applyPatchToCustomer(patch, user.get());
            users.set(users.indexOf(user.get()), userPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}
