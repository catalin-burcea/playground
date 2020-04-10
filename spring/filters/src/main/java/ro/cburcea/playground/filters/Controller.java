package ro.cburcea.playground.filters;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/filters")
    public String filters() {
        return "testing filter mechanism";
    }

    @GetMapping("/filters-error")
    public String filtersError() {
        throw new RuntimeException();
    }

    @GetMapping("/specific-filter")
    public String specificFilter() {
        return "testing url based filter mechanism";
    }
}
