package ro.cburcea.playground.scheduling.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Override the Executor at the Application Level
 * <p>
 * When a method return type is a Future, exception handling is easy – Future.get() method will throw the exception.
 * But, if the return type is void, exceptions will not be propagated to the calling thread.
 * Hence we need to add extra configurations to handle exceptions.
 */
@Configuration
//@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true)
@EnableAsync
public class ApplicationLevelConfiguration implements AsyncConfigurer {

    @Bean
    @Primary
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

//    @Override
//    public Executor getAsyncExecutor() {
//        return new ThreadPoolTaskExecutor();
//    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
