package ro.cburcea.playground.spring.interceptors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @RequestMapping(value = "/message")
    public String getMessage() {
        return "message";
    }
}