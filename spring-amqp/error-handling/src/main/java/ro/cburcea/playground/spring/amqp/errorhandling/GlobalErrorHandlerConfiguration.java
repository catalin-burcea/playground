package ro.cburcea.playground.spring.amqp.errorhandling;

import org.slf4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ErrorHandler;
import ro.cburcea.playground.spring.amqp.Foo;

import static ro.cburcea.playground.spring.amqp.errorhandling.ErrorHandlerApp.GLOBAL_ERROR_HANDLER_QUEUE;

@Configuration
public class GlobalErrorHandlerConfiguration {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalErrorHandlerConfiguration.class);

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
    }

    @Bean
    public Queue globalErrorHandlingQueue() {
        return new Queue(GLOBAL_ERROR_HANDLER_QUEUE, false, false, true);
    }

    @Bean
    @Primary
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * we will see an infinite number of such messages in the output. The messaged are requeued indefinitely
     */
//    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handle(Foo in) throws BusinessException {
        throw new BusinessException();
    }

    /**
     * Throw an AmqpRejectAndDontRequeueException – this might be useful for messages that won’t make sense in the future, so they can be discarded.
     */
    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handle2(Foo in) {
        throw new AmqpRejectAndDontRequeueException("reject and don't requeue exception");
    }

//    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handleSuccess(Foo in) {
        LOGGER.info("Received: {}", in);
    }
}
