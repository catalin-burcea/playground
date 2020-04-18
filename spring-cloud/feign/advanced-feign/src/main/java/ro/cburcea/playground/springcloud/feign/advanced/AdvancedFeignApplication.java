package ro.cburcea.playground.springcloud.feign.advanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdvancedFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedFeignApplication.class, args);
    }

}