package ro.cburcea.playground.redis.jedis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteEverythingInRedisIntegrationTest {

    private Jedis jedis;
    private RedisServer redisServer;
    private int port;

    @BeforeEach
    public void setUp() throws IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        redisServer = new RedisServer(port);
        redisServer.start();

        // Configure JEDIS
        jedis = new Jedis("localhost", port);
    }

    @AfterEach
    public void destroy() {
        redisServer.stop();
    }

    @Test
    public void whenPutDataIntoRedis_thenCanBeFound() {
        String key = "key";
        String value = "value";

        jedis.set(key, value);
        String received = jedis.get(key);

        assertEquals(value, received);
    }

    @Test
    public void whenPutDataIntoRedisAndThenFlush_thenCannotBeFound() {
        String key = "key";
        String value = "value";

        jedis.set(key, value);

        jedis.flushDB();

        String received = jedis.get(key);

        assertNull(received);
    }

    @Test
    public void whenPutDataIntoMultipleDatabases_thenFlushAllRemovesAll() {
        // add keys in different databases
        jedis.select(0);
        jedis.set("key1", "value1");
        jedis.select(1);
        jedis.set("key2", "value2");

        // we'll find the correct keys in the correct dbs
        jedis.select(0);
        assertEquals("value1", jedis.get("key1"));
        assertNull(jedis.get("key2"));

        jedis.select(1);
        assertEquals("value2", jedis.get("key2"));
        assertNull(jedis.get("key1"));

        // then, when we flush
        jedis.flushAll();

        // the keys will have gone
        jedis.select(0);
        assertNull(jedis.get("key1"));
        assertNull(jedis.get("key2"));
        jedis.select(1);
        assertNull(jedis.get("key1"));
        assertNull(jedis.get("key2"));
    }
}