package ro.cburcea.playground.springsecurity.oauth.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:8080/oauth/authorize
 * http://localhost:8080/oauth/token
 *
 * PASSWORD_CREDENTIALS grant type
 * 		using CURL:
 * 			[POST] curl reader:secret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=password
 * 			[POST] curl writer:secret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=password
 * 			[POST] curl noscopes:secret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=password
 * 		using Postman:
 			[POST] RequestHeaders: Authorization: Basic c2NyaXB0OnNlY3JldA==
 * 			RequestBody:
 * 				grant_type: "client_credentials"
 * 				scope: "message_read"
 * 				username: "user"
 * 				password: "password"
 *
 * 	CLIENT_CREDENTIALS grant type
 * 		using CURL:
 * 			[POST] curl script:secret@localhost:8080/oauth/token -d grant_type=client_credentials
 * 		using Postman:
 * 			[POST] RequestHeaders: Authorization: Basic c2NyaXB0OnNlY3JldA==
 * 			RequestBody:
 * 				grant_type: "authorization_code"
 * 				scope: "message_read"
 *
 * 	AUTHORIZATION_CODE grant type
 		using Postman:
 * 			[POST] RequestHeaders: Authorization: Basic c2NyaXB0OnNlY3JldA==
 * 			RequestBody:
 * 				grant_type: "authorization_code"
 * 				code: "ZgeZbL"
 * 				redirect_uri: "http://localhost:8081/login/client-app"
 * 				client_id: "web"
 *
 */
@SpringBootApplication
public class OAuth2AuthServerApp {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2AuthServerApp.class, args);
	}
}