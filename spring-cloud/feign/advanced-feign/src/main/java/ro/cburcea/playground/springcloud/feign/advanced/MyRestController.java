package ro.cburcea.playground.springcloud.feign.advanced;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.cburcea.playground.springcloud.feign.advanced.model.Post;

import java.util.List;

@RestController
public class MyRestController {

    @Autowired
    private JSONPlaceHolderClient jsonPlaceHolderClient;

    @GetMapping("/posts")
    List<Post> getPosts() {
        return jsonPlaceHolderClient.getPosts();
    }

    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId) {
        return jsonPlaceHolderClient.getPostById(postId);
    }

}
