package ro.cburcea.playground.springsecurity.oauth.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Spring Security OAuth does not support JWKs, nor does @EnableAuthorizationServer support adding more OAuth 2.0 API endpoints to its initial set.
     * However, we can add this with only a few lines.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .mvcMatchers("/.well-known/jwks.json").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic()
                .and()
            .csrf().ignoringRequestMatchers(request -> "/introspection".equals(request.getRequestURI()))
                .and()
            .formLogin()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true);
        // @formatter:on
    }

    /**
     * End users are specified in a WebSecurityConfigurerAdapter through a UserDetailsService.
     * So, if you use the OAuth2 Boot defaults (meaning you haven’t implemented a AuthorizationServerConfigurer),
     * you can expose a UserDetailsService and be done.
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // @formatter:off
        return new InMemoryUserDetailsManager(
            User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build(),
            User
                .withUsername("user2")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build());
        // @formatter:onn
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
