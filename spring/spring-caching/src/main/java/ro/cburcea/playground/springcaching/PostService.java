package ro.cburcea.playground.springcaching;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@CacheConfig
public class PostService {

    private static RestTemplate restTemplate = new RestTemplate();

    @Cacheable("posts")
    public Post[] getPosts() {
        System.out.println("starting getPosts()");
        String url = "https://jsonplaceholder.typicode.com/posts";
        return restTemplate.getForObject(url, Post[].class);
    }

    @Cacheable(value = "comments", key = "#post.id")
    public Comment[] getComments(Post post) {
        System.out.println("starting getComments(), post=" + post.getId());
        String url = String.format("https://jsonplaceholder.typicode.com/posts/%s/comments", post.getId());
        return restTemplate.getForObject(url, Comment[].class);
    }

    @CacheEvict(value = "posts", allEntries = true)
    public boolean resetCache() {
        return true;
    }

    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "comments", allEntries = true)
    })
    public boolean resetMultipleCaches() {
        return true;
    }

    @CachePut(value = "posts", condition = "#id == 1", key = "#id")
    public Post getPost(Integer id) {
        System.out.println("starting getPost(), id=" + id);
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        return restTemplate.getForObject(url, Post.class);
    }

}
