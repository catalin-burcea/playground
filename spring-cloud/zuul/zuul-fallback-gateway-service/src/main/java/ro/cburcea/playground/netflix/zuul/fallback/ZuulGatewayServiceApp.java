package ro.cburcea.playground.netflix.zuul.fallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayServiceApp {
 
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayServiceApp.class, args);
    }
}