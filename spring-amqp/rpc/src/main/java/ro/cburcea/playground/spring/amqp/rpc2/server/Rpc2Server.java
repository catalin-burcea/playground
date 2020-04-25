package ro.cburcea.playground.spring.amqp.rpc2.server;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ro.cburcea.playground.spring.amqp.rpc2.HelloService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Rpc2Server {

    @Bean
    HelloService helloService() {
        return new HelloServiceImpl();
    }

    @Bean
    Queue queue() {
        return new Queue("remotingQueue");
    }

    @Bean
    AmqpInvokerServiceExporter exporter(HelloService implementation, AmqpTemplate template) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(HelloService.class);
        exporter.setService(implementation);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean
    SimpleMessageListenerContainer listener(ConnectionFactory factory, AmqpInvokerServiceExporter exporter, Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setMessageListener(exporter);
        // Set the name of the queue(s) to receive messages from
        container.setQueueNames(queue.getName());
        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(Rpc2Server.class, args);
    }

}