package ro.cburcea.playground.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public FilterRegistrationBean<URLSpecificFilter> loggingFilter() {
        FilterRegistrationBean<URLSpecificFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new URLSpecificFilter());
        registrationBean.addUrlPatterns("/specific-filter/*");

        return registrationBean;
    }
}
