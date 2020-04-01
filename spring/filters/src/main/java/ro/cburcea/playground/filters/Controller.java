package ro.cburcea.playground.filters;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/filters")
    public String getSomeString() {
        return "testing filter mechanism";
    }

    @GetMapping("/specific-filter")
    public String getAnotherString() {
        return "testing url based filter mechanism";
    }
}
