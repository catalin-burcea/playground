package ro.cburcea.playground.netflix.zuul.ratelimiting;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;

@EnableZuulProxy
@SpringCloudApplication
public class ZuulRatelimitDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulRatelimitDemoApplication.class, args);
    }

    @Bean
    public RateLimitKeyGenerator rateLimitKeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        return new DefaultRateLimitKeyGenerator(properties, rateLimitUtils) {
            @Override
            public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
                return super.key(request, route, policy) + "_" + request.getMethod();
            }
        };
    }

    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                // implementation
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                // implementation
            }

            @Override
            public void handleError(String msg, Exception e) {
                // implementation
            }
        };
    }
}