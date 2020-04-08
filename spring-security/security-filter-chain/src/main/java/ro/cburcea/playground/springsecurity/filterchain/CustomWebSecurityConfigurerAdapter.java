package ro.cburcea.playground.springsecurity.filterchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("user1")
                    .password(passwordEncoder().encode("user1"))
                    .authorities("ROLE_USER")
                    .and()
                .withUser("user2")
                    .password(passwordEncoder().encode("user2"))
                    .authorities("ROLE_USER");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .httpBasic();

        httpSecurity.addFilterBefore(new LoggingRequestFilter(), SecurityContextPersistenceFilter.class);

        //Adds a Filter that must be an instance of or extend one of the Filters provided within the Security framework.
//        httpSecurity.addFilter(new LoggingRequestFilter());
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}