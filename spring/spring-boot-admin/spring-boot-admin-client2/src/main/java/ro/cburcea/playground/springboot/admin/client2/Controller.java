package ro.cburcea.playground.springboot.admin.client2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/test")
    public String getString() {
        return "test";
    }
}
