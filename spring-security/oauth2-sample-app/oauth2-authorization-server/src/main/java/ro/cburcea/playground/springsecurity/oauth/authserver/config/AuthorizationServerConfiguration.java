package ro.cburcea.playground.springsecurity.oauth.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;

/**
 * An instance of Legacy Authorization Server (spring-security-oauth2) that uses a single,
 * not-rotating key and exposes a JWK endpoint.
 * <p>
 * See
 * <a
 * target="_blank"
 * href="https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/">
 * Spring Security OAuth Autoconfig's documentation</a> for additional detail
 *
 * @since 5.1
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String REDIRECT_URI = "http://localhost:8081/login/client-app";
    private AuthenticationManager authenticationManager;
    private KeyPair keyPair;
    private boolean jwtEnabled;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthorizationServerConfiguration(
            AuthenticationConfiguration authenticationConfiguration,
            KeyPair keyPair,
            @Value("${security.oauth2.authorizationserver.jwt.enabled:true}") boolean jwtEnabled) throws Exception {

        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.keyPair = keyPair;
        this.jwtEnabled = jwtEnabled;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("reader")
                    .authorizedGrantTypes("password")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_read")
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("writer")
                    .authorizedGrantTypes("password")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_write")
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("script")
                    .authorizedGrantTypes("client_credentials")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_read")
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("web")
                    .authorizedGrantTypes("authorization_code")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_read", "message_write")
                    .redirectUris(REDIRECT_URI)
                    .accessTokenValiditySeconds(1000)
                    .refreshTokenValiditySeconds(600_600_000)
                    .and()
                .withClient("mobile")
                    .authorizedGrantTypes("implicit")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_read", "message_write")
                    .redirectUris(REDIRECT_URI)
                    .accessTokenValiditySeconds(1000)
                    .and()
                .withClient("refresh")
                    .authorizedGrantTypes("refresh_token")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("message_read", "message_write")
                    .redirectUris(REDIRECT_URI)
                    .accessTokenValiditySeconds(1000)
                    .refreshTokenValiditySeconds(100_000)
                    .and()
                .withClient("noscopes")
                    .authorizedGrantTypes("password")
                    .secret(passwordEncoder.encode("secret"))
                    .scopes("none")
                    .accessTokenValiditySeconds(600_000_000);
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // @formatter:off
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore());

        if (this.jwtEnabled) {
            endpoints.accessTokenConverter(accessTokenConverter());
        }
        // @formatter:on
    }

    @Bean
    public TokenStore tokenStore() {
        if (this.jwtEnabled) {
            return new JwtTokenStore(accessTokenConverter());
        } else {
            return new InMemoryTokenStore();
        }
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair);

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }
}