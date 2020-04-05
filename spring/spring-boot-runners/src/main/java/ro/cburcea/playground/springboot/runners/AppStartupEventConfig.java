package ro.cburcea.playground.springboot.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupEventConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppStartupEventConfig.class);

    @Bean
    public ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener() {
        return evt -> LOG.info("EXECUTING : onApplicationReadyEventListener");
    }
}
