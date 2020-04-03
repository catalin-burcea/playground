package ro.cburcea.playground.springboot.devtools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevToolsApp {

    public static void main(String[] args) {
//        If you need to completely disable restart support
//        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DevToolsApp.class, args);
    }
}
