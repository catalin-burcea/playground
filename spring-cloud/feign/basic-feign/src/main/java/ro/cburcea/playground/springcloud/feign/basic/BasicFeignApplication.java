package ro.cburcea.playground.springcloud.feign.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BasicFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicFeignApplication.class, args);
    }

}