package ro.cburcea.playground.springcloud.ribbon.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RibbonClient(name = "demoserver", configuration = RibbonConfiguration.class)
public class RibbonClientConfiguration {


    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.setReadTimeout(Duration.ofSeconds(1));
        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(1));
        return restTemplateBuilder.build();
    }

}
