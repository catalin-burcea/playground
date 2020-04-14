package ro.cburcea.playground.netflix.backendservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FooController {

    @GetMapping("/foos/{id}")
    public Foo findById(@PathVariable long id, HttpServletRequest req, HttpServletResponse res) {
        if (req.getHeader("test-header") != null) {
            res.addHeader("hello-response-header", "Hello, " + req.getHeader("test-header"));
        }
        return new Foo(id, "foo" + id);
    }
}