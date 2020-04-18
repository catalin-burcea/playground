package ro.cburcea.playground.springcloud.feign.basic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.cburcea.playground.springcloud.feign.basic.model.Post;

import java.util.List;

@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/", configuration = MyClientConfiguration.class)
public interface JSONPlaceHolderClient {
 
    @GetMapping(value = "/posts")
    List<Post> getPosts();
 
    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);

}