package ro.cburcea.playground.spring.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ProductServiceInterceptorAppConfig implements WebMvcConfigurer {

    @Autowired
    LoggerInterceptor loggerInterceptor;

    @Autowired
    AnotherLoggerInterceptor anotherLoggerInterceptor;

    @Autowired
    AnotherLoggerInterceptor2 anotherLoggerInterceptor2;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(anotherLoggerInterceptor);
        registry.addInterceptor(anotherLoggerInterceptor2);
    }
}