package ro.cburcea.playground.spring.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        users.add(new User(1, "name1"));
        users.add(new User(2, "name2"));
        users.add(new User(3, "name3"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
        return user.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(user.get());
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {
        Optional<User> user = users
                .stream()
                .filter(u -> newUser.getId().equals(u.getId()))
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
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        users.remove(user.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Integer id) {
        Optional<User> user = users
                .stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();

        if (user.isEmpty()) {
            users.add(newUser);
            return ResponseEntity
                    .created(URI.create("/users/" + newUser.getId()))
                    .build();
        }
        user.get().setName(newUser.getName());
        return ResponseEntity.noContent().build();
    }
}
