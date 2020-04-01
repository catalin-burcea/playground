package ro.cburcea.playground.spring.embeddedserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class EmbeddedServerConfigurationApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmbeddedServerConfigurationApp.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "9991"));
        app.run(args);
    }
}
