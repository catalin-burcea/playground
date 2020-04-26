package ro.cburcea.playground.spring.amqp.errorhandling;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ro.cburcea.playground.spring.amqp.Foo;

@SpringBootApplication
public class ErrorHandlerApp {

    public static final String GLOBAL_ERROR_HANDLER_QUEUE = "global.error.handler.queue";

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ErrorHandlerApp.class, args);
        context.getBean(ErrorHandlerApp.class).sendMessages();
        Thread.sleep(10000);
        context.close();
    }

    private void sendMessages() {
        this.template.convertAndSend(GLOBAL_ERROR_HANDLER_QUEUE, new Foo("bar"), m -> new Message("some bad json".getBytes(), m.getMessageProperties()));
    }

}