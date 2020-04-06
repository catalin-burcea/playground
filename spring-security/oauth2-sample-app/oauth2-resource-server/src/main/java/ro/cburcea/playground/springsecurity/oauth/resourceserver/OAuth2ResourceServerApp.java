package ro.cburcea.playground.springsecurity.oauth.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:8080/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=web&state=1234
 */
@SpringBootApplication
public class OAuth2ResourceServerApp {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourceServerApp.class, args);
	}
}