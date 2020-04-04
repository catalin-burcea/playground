package ro.cburcea.playground.springsecurity.basicauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/home")
    public String homepage() {
        return "home page";
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }

}
