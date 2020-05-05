package ro.cburcea.playground.springcloud.ribbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    Environment environment;

    @GetMapping("/")
    public String health() {
        return "I am Ok";
    }

    @GetMapping("/backend")
    public String backend() {
        System.out.println("Inside MyRestController::backend...");
        String serverPort = environment.getProperty("local.server.port");
        System.out.println("Port : " + serverPort);

        return "Hello from Backend - Host : localhost - Port : " + serverPort;
    }

    @GetMapping("/weather")
    public ResponseEntity<String> weather() {
        LOGGER.info("Providing today's weather information");
        if (isServiceUnavailable()) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        LOGGER.info("Today's a sunny day");
        return new ResponseEntity<>("Today's a sunny day", HttpStatus.OK);
    }

    private boolean isServiceUnavailable() {
        return new Random().nextInt(5) > 0;
    }
}
