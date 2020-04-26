package ro.cburcea.playground.spring.amqp.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ro.cburcea.playground.spring.amqp.Foo;

@SpringBootApplication
public class RetryApp {

    public static final String GLOBAL_ERROR_HANDLER_QUEUE = "global.error.handler.queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryConfiguration.class);

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(RetryApp.class, args);
        context.getBean(RetryApp.class).sendMessages();
        Thread.sleep(10000);
        context.close();
    }

    private void sendMessages() {
        this.template.convertAndSend(GLOBAL_ERROR_HANDLER_QUEUE, new Foo("bar"));
    }

    @RabbitListener(queues = GLOBAL_ERROR_HANDLER_QUEUE, containerFactory = "retryContainerFactory")
    public void consumeBlocking(String payload) throws Exception {
        LOGGER.info("Processing message from blocking-queue: {}", payload);

        throw new Exception("exception occurred!");
    }

}