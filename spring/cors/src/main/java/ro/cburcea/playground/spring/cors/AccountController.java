package ro.cburcea.playground.spring.cors;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @CrossOrigin(origins = "http://example.com")
    @GetMapping("/{id}")
    public String retrieve(@PathVariable Long id) {
        return "account" + id;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        System.out.println("account removed");
    }
}