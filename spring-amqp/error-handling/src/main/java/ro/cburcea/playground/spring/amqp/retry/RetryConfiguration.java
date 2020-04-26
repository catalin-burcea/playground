package ro.cburcea.playground.spring.amqp.retry;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import static ro.cburcea.playground.spring.amqp.retry.RetryApp.GLOBAL_ERROR_HANDLER_QUEUE;

@Configuration
public class RetryConfiguration {

    @Bean
    public SimpleRabbitListenerContainerFactory retryContainerFactory(ConnectionFactory connectionFactory,
                                                                      RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        Advice[] adviceChain = {retryInterceptor};
        factory.setAdviceChain(adviceChain);

        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(1000, 1.5, 10000)
                .maxAttempts(4)
                .recoverer(observableRecoverer())
                .build();
    }

    @Bean
    public CustomRejectAndDontRequeueRecoverer observableRecoverer() {
        return new CustomRejectAndDontRequeueRecoverer();
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue(GLOBAL_ERROR_HANDLER_QUEUE, false, false, true);
    }
}
