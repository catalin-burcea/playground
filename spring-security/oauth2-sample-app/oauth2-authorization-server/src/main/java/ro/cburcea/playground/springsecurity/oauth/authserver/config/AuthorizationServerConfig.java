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
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;
import java.util.Collections;
import java.util.Map;

import static ro.cburcea.playground.springsecurity.oauth.authserver.endpoints.JwkSetEndpoint.RSA_KEY_1;

/**
 * Up until 2019, the OAuth 2.0 spec only recommended using the PKCE extension for mobile and JavaScript apps.
 * The latest OAuth Security BCP now recommends using PKCE also for server-side apps,
 * as it provides some additional benefits there as well.
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String REDIRECT_URI = "http://localhost:8082/oauth2/redirect";
    private static final String RESOURCE_ID = "RESOURCE_SERVER_ID";
    private static final String PASSWORD_GRANT_TYPE = "password";
    private static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";
    private static final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    private static final String IMPLICIT_GRANT_TYPE = "implicit";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String MESSAGE_READ_SCOPE = "message_read";
    private static final String MESSAGE_WRITE_SCOPE = "message_write";
    private static final String SECRET = "secret";
    private static final String NONE_SCOPE = "none";

    private AuthenticationManager authenticationManager;
    private KeyPair keyPair;
    private boolean jwtEnabled;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthorizationServerConfig(
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
                    .authorizedGrantTypes(PASSWORD_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_READ_SCOPE)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("writer")
                    .authorizedGrantTypes(PASSWORD_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_WRITE_SCOPE)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("script")
                    .authorizedGrantTypes(CLIENT_CREDENTIALS_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_READ_SCOPE)
//                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("web")
                    .authorizedGrantTypes(AUTHORIZATION_CODE_GRANT_TYPE, REFRESH_TOKEN_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_READ_SCOPE, MESSAGE_WRITE_SCOPE)
                    .autoApprove(true)
                    .autoApprove(MESSAGE_READ_SCOPE, MESSAGE_WRITE_SCOPE)
                    .redirectUris(REDIRECT_URI)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(1000)
                    .refreshTokenValiditySeconds(600_600_000)
                    .and()
                .withClient("mobile")
                    .authorizedGrantTypes(IMPLICIT_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_READ_SCOPE, MESSAGE_WRITE_SCOPE)
                    .redirectUris(REDIRECT_URI)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(1000)
                    .and()
                .withClient("refresh")
                    .authorizedGrantTypes(REFRESH_TOKEN_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(MESSAGE_READ_SCOPE, MESSAGE_WRITE_SCOPE)
                    .redirectUris(REDIRECT_URI)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(1000)
                    .refreshTokenValiditySeconds(100_000)
                    .and()
                .withClient("noscopes")
                    .authorizedGrantTypes(PASSWORD_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .scopes(NONE_SCOPE)
                    .resourceIds(RESOURCE_ID)
                    .accessTokenValiditySeconds(600_000_000)
                    .and()
                .withClient("resource-server")
                    .authorizedGrantTypes(CLIENT_CREDENTIALS_GRANT_TYPE)
                    .secret(passwordEncoder.encode(SECRET))
                    .resourceIds(RESOURCE_ID)
                    .scopes(NONE_SCOPE)
                    .accessTokenValiditySeconds(600_000_000);
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // @formatter:off
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
        // @formatter:on
    }

    @Bean
    public TokenStore tokenStore() {
        if (this.jwtEnabled) {
            final JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
            jwtTokenStore.setApprovalStore(new InMemoryApprovalStore());
            return jwtTokenStore;
        } else {
            return new InMemoryTokenStore();
        }
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", RSA_KEY_1);
        JwtCustomHeadersAccessTokenConverter converter = new JwtCustomHeadersAccessTokenConverter(customHeaders, this.keyPair);

//        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
//        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
//        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }

//    // Token services. Needed for JWT
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        return defaultTokenServices;
//    }
}