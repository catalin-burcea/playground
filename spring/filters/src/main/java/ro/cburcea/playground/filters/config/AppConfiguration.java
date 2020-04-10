package ro.cburcea.playground.filters.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.cburcea.playground.filters.URLSpecificFilter;

@Configuration
public class AppConfiguration {

    /**
     * the web.xml servlet filter equivalent config
     */
    @Bean
    public FilterRegistrationBean<URLSpecificFilter> urlSpecificFilter() {
        FilterRegistrationBean<URLSpecificFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new URLSpecificFilter());
        registrationBean.addUrlPatterns("/specific-filter/*");

        return registrationBean;
    }
}
