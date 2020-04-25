package ro.cburcea.playground.spring.amqp.rpc.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class RpcClient {

    private int start = 0;

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(RpcClient.class, args);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rpc.exchange");
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println(" [x] Requesting fib(" + start + ")");
        Integer response = (Integer) template.convertSendAndReceive(exchange().getName(), "rpc", start++);
        System.out.println(" [.] Got '" + response + "'");
    }

}