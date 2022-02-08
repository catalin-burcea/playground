package ro.cburcea.playground.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SimplePojo simplePojo1() {
        return new SimplePojo(1);
    }

    @Bean
    public SimplePojo simplePojo2() {
        return new SimplePojo(2);
    }

    @Bean
    public SimplePojo simplePojo3() {
        return new SimplePojo(3);
    }

}
