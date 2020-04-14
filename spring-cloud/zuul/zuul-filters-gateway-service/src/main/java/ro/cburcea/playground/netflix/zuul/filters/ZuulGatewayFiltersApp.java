package ro.cburcea.playground.netflix.zuul.filters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayFiltersApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayFiltersApp.class, args);
    }
}