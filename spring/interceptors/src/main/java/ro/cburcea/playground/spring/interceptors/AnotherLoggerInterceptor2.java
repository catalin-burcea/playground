package ro.cburcea.playground.spring.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AnotherLoggerInterceptor2 implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AnotherLoggerInterceptor2.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        LOG.info("Pre Handle method is Calling");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

        LOG.info("Post Handle method is Calling from AnotherLoggerInterceptor2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object
            handler, Exception exception) {

        LOG.info("Request and Response is completed from AnotherLoggerInterceptor2");
    }
}