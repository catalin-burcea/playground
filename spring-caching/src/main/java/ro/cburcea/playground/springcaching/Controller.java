package ro.cburcea.playground.springcaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Post[]> getPosts() {
        return new ResponseEntity<Post[]>(postService.getPosts(), HttpStatus.OK);
    }
    @GetMapping("/posts/{id}/comments")
    public Comment[] getComments(@PathVariable Integer id) {
        Post post = postService.getPost(id);
        return postService.getComments(post);
    }

    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable Integer id) {
        return postService.getPost(id);
    }

    @GetMapping("/reset")
    public Boolean reset() {
        return postService.resetCache();
    }

    @GetMapping("/reset-multiple")
    public Boolean resetMultipleCaches() {
        return postService.resetMultipleCaches();
    }
}
