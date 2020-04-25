## About Spring AMQP
Spring AMQP consists of two modules: ``spring-amqp`` and ``spring-rabbit``
Our intention is to provide generic abstractions that do not rely on any particular AMQP broker implementation or client library. End user code can be more portable across vendor implementations as it can be developed against the abstraction layer only. These abstractions are then implemented by broker-specific modules, such as 'spring-rabbit'. There is currently only a RabbitMQ implementation.

### AMQP Abstractions:
* **Message**
* **Exchange**
* **Queue**
* **Binding**

### Key Components
**AmqpTemplate**

The interface that defines the main operations. Those operations cover the general behavior for sending and receiving messages. In other words, they are not unique to any implementation.

**RabbitTemplate** - An implementation of AmqpTemplate.

* **@RabbitListener**
* **@RabbitHandler**
* **@EnableRabbit**

**AnonymousQueue** - They are non-durable, exclusive, auto-delete queues.

**Temporary queues** - We want to hear about all messages, not just a subset of them. We're also interested only in currently flowing messages, not in the old ones. We need two things:
* Firstly, whenever we connect to Rabbit, we need a fresh, empty queue. To do this, we could create a queue with a random name, or -- even better -- let the server choose a random queue name for us.
* Secondly, once we disconnect the consumer, the queue should be automatically deleted. To do this with the Spring AMQP client, we defined an AnonymousQueue, which creates a non-durable, exclusive, auto-delete queue with a generated name.

**ConfirmCallback vs ReturnCallback**

If the message we send fails to reach the switch, that is to say, the sent switch has written an error, the confirm method will be called back immediately, and ack = false, which will also report to you cause.
If the message we send arrives at the switch, but the routing key is written incorrectly, and the switch fails to forward to the queue, confirm will be called back, and ack = true will be displayed, which means that the switch has received the message correctly, but at the same time, the returned Message method will be called, which will return the message we sent back.

**Message confirmation: Confirm mechanism vs Transaction mechanism**

We need to note that the transaction mechanism and confirm mechanism are mutually exclusive and cannot have both.

### rabbitmqctl commands

```rabbitmqctl list_queues```

```rabbitmqctl delete_queue name```

```rabbitmqctl purge_queue name```

```rabbitmqctl list_exchanges```

```rabbitmqctl list_consumers```

```rabbitmqctl list_bindings```

```rabbitmqctl list_channels```

```rabbitmqctl list_vhosts```

### Tools
* management HTTP API + [web management UI](https://www.rabbitmq.com/management.html) (http://localhost:15672/)
* rabbitmqadmin
* rabbitmqctl

```docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management```

## References:
https://www.rabbitmq.com/tutorials/amqp-concepts.html
https://www.rabbitmq.com/queues.html
https://www.rabbitmq.com/man/rabbitmqctl.8.html
https://www.rabbitmq.com/management-cli.html
https://www.rabbitmq.com/tutorials/tutorial-two-spring-amqp.html
https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html
https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp.html
https://www.rabbitmq.com/tutorials/tutorial-five-spring-amqp.html
https://www.rabbitmq.com/tutorials/tutorial-six-spring-amqp.html
https://github.com/spring-projects/spring-amqp-samples
https://programmer.group/spring-rabbit-mq-enables-publisher-confirm.html
https://www.baeldung.com/spring-amqp
https://www.baeldung.com/rabbitmq-spring-amqp
https://www.baeldung.com/spring-remoting-amqp
https://www.baeldung.com/spring-amqp-error-handling
https://www.baeldung.com/spring-amqp-exponential-backoff


