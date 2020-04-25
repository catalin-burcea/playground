package ro.cburcea.playground.spring.amqp.rpc.server;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RpcServer {

    private static final String RPC_REQUESTS_QUEUE = "rpc.requests";

    public static void main(String[] args) {
        SpringApplication.run(RpcServer.class, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(RPC_REQUESTS_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rpc.exchange");
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("rpc");
    }

    @RabbitListener(queues = RPC_REQUESTS_QUEUE)
//    @SendTo("rpc.replies") //used when the client doesn't set replyTo.
    public int fibonacci(int n) {
        System.out.println(" [x] Received request for " + n);
        int result = fib(n);
        System.out.println(" [.] Returned " + result);
        return result;
    }

    public int fib(int n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
    }

}