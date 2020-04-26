package ro.cburcea.playground.spring.amqp.retry;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryConfiguration.class);

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

    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE, containerFactory = "retryContainerFactory")
    public void consumeBlocking(String payload) throws Exception {
        LOGGER.info("Processing message from blocking-queue: {}", payload);

        throw new Exception("exception occurred!");
    }
}
