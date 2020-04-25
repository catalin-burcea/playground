package ro.cburcea.playground.spring.amqp.rpc2.server;

import ro.cburcea.playground.spring.amqp.rpc2.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting() {
        return "Hello from RPC implementation with Spring AMQP";
    }
}
