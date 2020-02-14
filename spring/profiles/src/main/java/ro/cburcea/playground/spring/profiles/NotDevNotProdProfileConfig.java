package ro.cburcea.playground.spring.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"!dev & !prod"})
public class NotDevNotProdProfileConfig {

    @Value("${props.user.name}")
    private String username;

    @Bean
    public ProfileBean notDevNotProdProfileBean() {
        System.out.println("load a !dev && !prod profile bean; user: " + username);
        return new ProfileBean("not_dev_not_prod_bean");
    }

}