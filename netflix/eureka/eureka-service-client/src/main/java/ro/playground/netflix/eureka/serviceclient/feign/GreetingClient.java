package ro.playground.netflix.eureka.serviceclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("feign")
@FeignClient("spring-cloud-eureka-client")
public interface GreetingClient {

    @RequestMapping("/greeting")
    String greeting();

}