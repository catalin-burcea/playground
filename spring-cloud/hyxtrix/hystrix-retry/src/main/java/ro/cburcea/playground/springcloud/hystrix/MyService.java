package ro.cburcea.playground.springcloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {

    @Autowired
    private RestTemplate restTemplate;

    private String clientName = "demoserver";

    @HystrixCommand(fallbackMethod = "fallback")
    public String success() {
        String result = this.restTemplate.getForObject("http://" + clientName + "/backend", String.class);
        return "Server Response :: " + result;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public String retry() {
        String result = this.restTemplate.getForObject("http://" + clientName + "/timeout", String.class);
        return "Server Response :: " + result;
    }

    public String fallback() {
        return "fallback from hystrix";
    }
}
