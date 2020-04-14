package ro.cburcea.playground.springresilience4j.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class Resilience4jSpringAnnotationsController {

    @Autowired
    private Resilience4jSpringAnnotationsService service;

    @GetMapping("/success")
    public String success() {
        return service.success();
    }

    @GetMapping("/successException")
    public String successException() {
        return service.successException();
    }

    @GetMapping("/futureSuccess")
    public String futureSuccess() throws ExecutionException, InterruptedException {
        return service.futureSuccess().get();
    }

    @GetMapping("/failure")
    public String failure() {
        return service.failure();
    }

    @GetMapping("/failureWithFallback")
    public String failureWithFallback() {
        return service.failureWithFallback();
    }

}