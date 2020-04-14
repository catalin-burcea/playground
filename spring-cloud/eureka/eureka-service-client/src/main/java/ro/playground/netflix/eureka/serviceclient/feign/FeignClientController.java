package ro.playground.netflix.eureka.serviceclient.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@RestController
@Profile("feign")
public class FeignClientController {

    @Autowired
    private GreetingClient greetingClient;

    @RequestMapping("/greeting")
    public String greeting() {
        return "FEIGN:" + greetingClient.greeting();
    }

}