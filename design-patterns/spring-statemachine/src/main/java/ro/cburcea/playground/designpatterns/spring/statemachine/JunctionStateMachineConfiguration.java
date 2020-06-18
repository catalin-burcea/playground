package ro.cburcea.playground.designpatterns.spring.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

@Configuration
@EnableStateMachine
public class JunctionStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        // @formatter:off
        states
                .withStates()
                    .initial("SI")
                    .junction("SJ")
                    .state("high")
                    .state("medium")
                    .state("low")
                    .end("SF");
        // @formatter:on
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        // @formatter:off
        transitions
                .withExternal()
                    .source("SI")
                    .target("SJ")
                    .event("E1")
                    .and()
                .withJunction()
                    .source("SJ")
                    .first("high", highGuard())
                    .then("medium", mediumGuard())
                    .last("low")
                    .and()
                .withExternal()
                    .source("low")
                    .target("SF")
                    .event("end");
        // @formatter:on
    }

    /**
     * A guard can be used to validate some data before a transition to a state is executed.
     */
    @Bean
    public Guard<String, String> mediumGuard() {
        return ctx -> false;
    }

    @Bean
    public Guard<String, String> highGuard() {
        return ctx -> false;
    }
}