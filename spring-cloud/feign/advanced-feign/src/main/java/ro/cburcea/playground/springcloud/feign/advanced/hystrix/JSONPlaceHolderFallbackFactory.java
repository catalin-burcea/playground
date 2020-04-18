package ro.cburcea.playground.springcloud.feign.advanced.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import ro.cburcea.playground.springcloud.feign.advanced.JSONPlaceHolderClient;
import ro.cburcea.playground.springcloud.feign.advanced.model.Post;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallbackFactory implements FallbackFactory<JSONPlaceHolderClient> {

    @Override
    public JSONPlaceHolderClient create(Throwable cause) {
        return new JSONPlaceHolderClient() {

            @Override
            public List<Post> getPosts() {
                return Collections.emptyList();
            }

            @Override
            public Post getPostById(Long postId) {
                Post post = new Post();
                post.setId(postId);
                post.setTitle("Default Post2");
                post.setBody("Default Body2");
                post.setException(cause.getMessage());
                return post;
            }
        };
    }
}