package ro.cburcea.playground.springcloud.feign.advanced.hystrix;

import org.springframework.stereotype.Component;
import ro.cburcea.playground.springcloud.feign.advanced.JSONPlaceHolderClient;
import ro.cburcea.playground.springcloud.feign.advanced.model.Post;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Default Post");
        post.setBody("Default Body");
        return post;
    }
}