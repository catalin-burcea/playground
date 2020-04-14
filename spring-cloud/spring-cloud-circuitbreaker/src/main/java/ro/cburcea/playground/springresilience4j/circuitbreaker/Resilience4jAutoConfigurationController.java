package ro.cburcea.playground.springresilience4j.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resilience4jAutoConfigurationController {

    @Autowired
    private Resilience4JAutoConfigurationService service;

    @GetMapping("/success")
    public String success() {
        return service.remoteCallWithSuccess();
    }

    @GetMapping("/fallback")
    public String fallback() {
        return service.remoteCallWithFallback();
    }

    @GetMapping("/exception")
    public String exception() {
        return service.remoteCallWithException();
    }

    @GetMapping("/metrics")
    public String getMetrics() {
        return service.getMetrics();
    }

}