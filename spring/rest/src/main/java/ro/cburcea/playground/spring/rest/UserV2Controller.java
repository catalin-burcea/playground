package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.spring.rest.dtos.UserV2Dto;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserV2Controller {

    private static final String API_V2 = "application/vnd.rest.v2+json";
    private static final String APPLICATION_JSON_PATCH_V2_JSON = "application/json-patchv2+json";


    @Autowired
    private UserService userService;

    // built-in support for HEAD and OPTIONS for GET methods
    @GetMapping(value = "/users", produces = API_V2)
    public ResponseEntity<List<UserV2Dto>> getUsersV2() {
        return ResponseEntity.ok(UserMapper.INSTANCE.mapToUsersV2(userService.findAllUsers()));
    }


    @GetMapping(value = "/users/{id}", produces = API_V2)
    public ResponseEntity<UserV2Dto> getUserV2(@PathVariable int id) {
        Optional<User> user = userService.findUserById(id);
        return user
                .map(u -> ResponseEntity.ok(UserMapper.INSTANCE.mapToUserV2Dto(u)))
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * POST request creates a resource. The server assigns a URI for the new resource, and returns that URI to the client.
     * In the REST model, you frequently apply POST requests to collections. The new resource is added to the collection.
     * A POST request can also be used to submit data for processing to an existing resource, without any new resource being created.
     */
    @PostMapping(value = "/users", consumes = API_V2)
    public ResponseEntity<Void> addUser(@RequestBody UserV2Dto newV2User) {
        Optional<User> user = userService.findUserById(newV2User.getId());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userService.findAllUsers().add(UserMapper.INSTANCE.mapToUser(newV2User));
        return ResponseEntity
                .created(URI.create("/users/" + newV2User.getId()))
                .build();
    }

    @PutMapping(value = "/users/{id}", consumes = API_V2)
    public ResponseEntity<Void> updateUser(@RequestBody UserV2Dto userV2Dto, @PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

        if (user.isEmpty()) {
            userService.findAllUsers().add(UserMapper.INSTANCE.mapToUser(userV2Dto));
            return ResponseEntity
                    .created(URI.create("/users/" + userV2Dto.getId()))
                    .build();
        }
        user.get().setFirstName(userV2Dto.getName().split(" ")[0]);
        user.get().setLastName(userV2Dto.getName().split(" ")[1]);
        user.get().setAge(userV2Dto.getAge());
        user.get().setSports(userV2Dto.getSports());
        return ResponseEntity.noContent().build();
    }

    /**
     *  PATCH method
     *  Content-Type = application/json-patchv2+json
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
             "op":"replace",
             "path":"/name",
             "value":"Fooo Baaar"
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
    @PatchMapping(value = "/users/{id}", consumes = {APPLICATION_JSON_PATCH_V2_JSON})
    public ResponseEntity<Void> patchUserMethod2(@RequestBody JsonPatch patch, @PathVariable int id) {
        Optional<User> user = userService.findUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            UserV2Dto userV2DtoPatched = UserUtils.applyPatchToUserV2(patch, UserMapper.INSTANCE.mapToUserV2Dto(user.get()));
            userService.findAllUsers().set(userService.findAllUsers().indexOf(user.get()), UserMapper.INSTANCE.mapToUser(userV2DtoPatched));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}
