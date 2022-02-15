package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.spring.rest.dtos.UserDto;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private static final String API_V1 = "application/vnd.rest.v1+json";
    private static final String APPLICATION_JSON_PATCH_V1_JSON = "application/json-patchv1+json";

    @Autowired
    private UserService userService;

    // built-in support for HEAD and OPTIONS for GET methods
    @GetMapping(value = "/users", produces = API_V1)
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(UserMapper.INSTANCE.mapToUsers(userService.findAllUsers()));
    }


    @GetMapping(value = "/users/{id}", produces = API_V1)
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        Optional<User> user = userService.findUserById(id);
        return user
                .map(u -> ResponseEntity.ok(UserMapper.INSTANCE.mapToUserDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * POST request creates a resource. The server assigns a URI for the new resource, and returns that URI to the client.
     * In the REST model, you frequently apply POST requests to collections. The new resource is added to the collection.
     * A POST request can also be used to submit data for processing to an existing resource, without any new resource being created.
     */
    @PostMapping(value = "/users", consumes = API_V1)
    public ResponseEntity<Void> addUser(@RequestBody UserDto newUser) {
        Optional<User> user = userService.findUserById(newUser.getId());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userService.findAllUsers().add(UserMapper.INSTANCE.mapToUser(newUser));
        return ResponseEntity
                .created(URI.create("/users/" + newUser.getId()))
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.findAllUsers().remove(user.get());
        return ResponseEntity.noContent().build();
    }

    /**
     * A PUT request creates a resource or updates an existing resource.
     * The client specifies the URI for the resource. The request body contains a complete representation of the resource.
     * If a resource with this URI already exists, it is replaced. Otherwise a new resource is created, if the server supports doing so.
     * PUT requests are most frequently applied to resources that are individual items, such as a specific customer,
     * rather than collections. A server might support updates but not creation via PUT. Whether to support creation via PUT
     * depends on whether the client can meaningfully assign a URI to a resource before it exists.
     * If not, then use POST to create resources and PUT or PATCH to update.
     */
    @PutMapping(value = "/users/{id}", consumes = API_V1)
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, @PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

        if (user.isEmpty()) {
            userService.findAllUsers().add(UserMapper.INSTANCE.mapToUser(userDto));
            return ResponseEntity
                    .created(URI.create("/users/" + userDto.getId()))
                    .build();
        }
        user.get().setFirstName(userDto.getFirstName());
        user.get().setLastName(userDto.getLastName());
        user.get().setAge(userDto.getAge());
        user.get().setSports(userDto.getSports());
        return ResponseEntity.noContent().build();
    }

    /**
     * A PATCH request performs a partial update to an existing resource. The client specifies the URI for the resource.
     * The request body specifies a set of changes to apply to the resource. This can be more efficient than using PUT,
     * because the client only sends the changes, not the entire representation of the resource.
     * Technically PATCH can also create a new resource (by specifying a set of updates to a "null" resource), if the server supports this.
     */
    @PatchMapping(value = "/users/{id}", consumes = API_V1)
    public ResponseEntity<Void> patchUserMethod1(@RequestBody UserDto patchedUser, @PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

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
        if (patchedUser.getSports() != null) {
            user.get().setSports(patchedUser.getSports());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH method
     * Content-Type = application/json-patchv1+json
     [
     {
         "op":"test",
         "path":"/age",
         "value": 77
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

    // do we need to version PATCH?!
    @PatchMapping(value = "/users/{id}", consumes = {APPLICATION_JSON_PATCH_V1_JSON})
    public ResponseEntity<Void> patchUserMethod2(@RequestBody JsonPatch patch, @PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            UserDto userDtoPatched = UserUtils.applyPatchToUser(patch, UserMapper.INSTANCE.mapToUserDto(user.get()));
            userService.findAllUsers().set(userService.findAllUsers().indexOf(user.get()), UserMapper.INSTANCE.mapToUser(userDtoPatched));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}
