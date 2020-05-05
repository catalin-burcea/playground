package ro.cburcea.playground.springcloud.ribbon.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RibbonClient(name = "weather-service", configuration = RibbonConfiguration.class)
public class RibbonClientConfiguration {

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1);
        clientHttpRequestFactory.setReadTimeout(1);
        return clientHttpRequestFactory;
    }

//    @LoadBalanced
//    @Bean
    RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.setReadTimeout(Duration.ofMillis(10));
        restTemplateBuilder.setConnectTimeout(Duration.ofMillis(10));
        return restTemplateBuilder.build();
    }

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
