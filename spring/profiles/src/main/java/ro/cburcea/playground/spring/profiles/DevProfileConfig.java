package ro.cburcea.playground.spring.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevProfileConfig {

    @Value("${props.user.name}")
    private String username;

    @Bean
    public ProfileBean devProfileBean() {
        System.out.println("load a dev profile bean; user: " + username);
        return new ProfileBean("dev_bean");
    }

}