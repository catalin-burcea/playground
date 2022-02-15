package ro.cburcea.playground.spring.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;


/*
 * https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design
 */
@SpringBootApplication
public class RestSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(RestSpringBootApp.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean () {
        ShallowEtagHeaderFilter eTagFilter = new ShallowEtagHeaderFilter();
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(eTagFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
