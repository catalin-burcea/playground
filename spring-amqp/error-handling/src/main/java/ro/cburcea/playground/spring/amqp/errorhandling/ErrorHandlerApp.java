package ro.cburcea.playground.spring.amqp.errorhandling;

import org.slf4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ro.cburcea.playground.spring.amqp.Foo;

@SpringBootApplication
public class ErrorHandlerApp {

    public static final String GLOBAL_ERROR_HANDLER_QUEUE = "global.error.handler.queue";
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalErrorHandlerConfiguration.class);


    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ErrorHandlerApp.class, args);
        context.getBean(ErrorHandlerApp.class).sendMessages();
        Thread.sleep(10000);
        context.close();
    }

    private void sendMessages() {
        this.template.convertAndSend(GLOBAL_ERROR_HANDLER_QUEUE, new Foo("bar"));
//        this.template.convertAndSend(GLOBAL_ERROR_HANDLER_QUEUE, new Foo("bar"), m -> new Message("some bad json".getBytes(), m.getMessageProperties()));
    }


    /**
     * we will see an infinite number of such messages in the output. The messaged are requeued indefinitely
     */
    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handle(Foo in) throws BusinessException {
        throw new BusinessException();
    }

    /**
     * Throw an AmqpRejectAndDontRequeueException – this might be useful for messages that won’t make sense in the future, so they can be discarded.
     */
//    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handle2(Foo in) {
        throw new AmqpRejectAndDontRequeueException("reject and don't requeue exception");
    }

    //    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE)
    public void handleSuccess(Foo in) {
        LOGGER.info("Received: {}", in);
    }

}