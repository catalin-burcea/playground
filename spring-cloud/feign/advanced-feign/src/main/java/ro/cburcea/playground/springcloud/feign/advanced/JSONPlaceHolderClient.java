package ro.cburcea.playground.springcloud.feign.advanced;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.cburcea.playground.springcloud.feign.advanced.hystrix.JSONPlaceHolderFallbackFactory;
import ro.cburcea.playground.springcloud.feign.advanced.model.Post;

import java.util.List;

/**
 * When using Feign with Hystrix fallbacks, there are multiple beans in the ApplicationContext of the same type.
 * This will cause @Autowired to not work because there isn’t exactly one bean, or one marked as primary.
 * To work around this, Spring Cloud Netflix marks all Feign instances as @Primary, so Spring Framework will know which bean to inject.
 * In some cases, this may not be desirable. To turn off this behavior set the primary attribute of @FeignClient to false.
 */
//@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/", fallback = JSONPlaceHolderFallback.class)
@FeignClient(
        value = "jplaceholder",
//        url = "http://localhost:8082",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = MyClientConfiguration.class,
        fallbackFactory = JSONPlaceHolderFallbackFactory.class)
public interface JSONPlaceHolderClient {

    @GetMapping(value = "/posts")
    List<Post> getPosts();

    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);

}