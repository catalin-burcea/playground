package ro.cburcea.playground.spring.data.redis.pubsub;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ContextConfiguration;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataRedisTest
@ContextConfiguration(classes = RedisConfig.class)
public class RedisMessageListenerIT {

    private static RedisServer redisServer;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @BeforeAll
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder()
                .port(6379)
                .setting("maxmemory 256M")
                .build();

        redisServer.start();
    }

    @AfterAll
    public static void stopRedisServer() {
        redisServer.stop();
    }

    @Test
    public void testOnMessage() throws Exception {
        String message1 = "Message " + UUID.randomUUID();
        String message2 = "Message " + UUID.randomUUID();
        redisMessagePublisher.publish(message1);
        redisMessagePublisher.publish(message2);
        Thread.sleep(1000);
        assertTrue(RedisMessageSubscriber.messageList.contains(message1));
        assertTrue(RedisMessageSubscriber.messageList.contains(message2));
    }
}