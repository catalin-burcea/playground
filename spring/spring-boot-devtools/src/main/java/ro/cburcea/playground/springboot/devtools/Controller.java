package ro.cburcea.playground.springboot.devtools;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("reload")
    public String reload() {
        return "reloaded2";
    }

}
