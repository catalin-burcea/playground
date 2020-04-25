package ro.cburcea.playground.spring.amqp.rpc2.client;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ro.cburcea.playground.spring.amqp.rpc2.HelloService;

@SpringBootApplication
public class Rpc2Client {

    private static final String REMOTING_EXCHANGE = "remoting.exchange";
    private static final String REMOTING_BINDING = "remoting.binding";
    private static final String REMOTING_QUEUE = "remotingQueue";

    @Bean
    Queue queue() {
        return new Queue(REMOTING_QUEUE);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(REMOTING_EXCHANGE);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(REMOTING_BINDING);
    }

    @Bean
    AmqpProxyFactoryBean amqpFactoryBean(AmqpTemplate amqpTemplate) {
        AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
        factoryBean.setServiceInterface(HelloService.class);
        factoryBean.setAmqpTemplate(amqpTemplate);
        return factoryBean;
    }

    @Bean
    RabbitTemplate amqpTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setRoutingKey(REMOTING_BINDING);
        template.setExchange(REMOTING_EXCHANGE);
        template.setReplyTimeout(10_000);
        return template;
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Rpc2Client.class, args);
        HelloService proxy = context.getBean(HelloService.class);
        System.out.println(proxy.greeting());
        context.close();
    }

}