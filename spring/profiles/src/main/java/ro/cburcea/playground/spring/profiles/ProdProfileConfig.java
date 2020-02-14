package ro.cburcea.playground.spring.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdProfileConfig {

    @Value("${props.user.name}")
    private String username;

    @Bean
    public ProfileBean prodProfileBean() {
        System.out.println("load a prod profile bean; user: " + username);
        return new ProfileBean("prod_bean");
    }
}