package ro.cburcea.playground.springsecurity.filterchain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/home")
    public String homepage() {
        return "home page";
    }


    @GetMapping("/secure")
    public String sayHello() {
        return "hello world from secured endpoint";
    }

}
