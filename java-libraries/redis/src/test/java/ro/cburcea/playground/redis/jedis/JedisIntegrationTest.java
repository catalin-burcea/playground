package ro.cburcea.playground.redis.jedis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.*;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class JedisIntegrationTest {

    private static Jedis jedis;
    private static RedisServer redisServer;
    private static int port;

    @BeforeAll
    public static void setUp() throws IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        redisServer = new RedisServer(port);
        redisServer.start();

        // Configure JEDIS
        jedis = new Jedis("localhost", port);
    }

    @AfterAll
    public static void destroy() {
        redisServer.stop();
    }

    @AfterEach
    public void flush() {
        jedis.flushAll();
    }

    @Test
    public void givenAString_thenSaveItAsRedisStrings() {
        String key = "key";
        String value = "value";

        jedis.set(key, value);
        String value2 = jedis.get(key);

        assertEquals(value, value2);
    }

    @Test
    public void givenListElements_thenSaveThemInRedisList() {
        String queue = "queue#tasks";

        String taskOne = "firstTask";
        String taskTwo = "secondTask";
        String taskThree = "thirdTask";

        jedis.lpush(queue, taskOne, taskTwo);

        String taskReturnedOne = jedis.rpop(queue);

        jedis.lpush(queue, taskThree);
        assertEquals(taskOne, taskReturnedOne);

        String taskReturnedTwo = jedis.rpop(queue);
        String taskReturnedThree = jedis.rpop(queue);

        assertEquals(taskTwo, taskReturnedTwo);
        assertEquals(taskThree, taskReturnedThree);

        String taskReturnedFour = jedis.rpop(queue);
        assertNull(taskReturnedFour);
    }

    @Test
    public void givenSetElements_thenSaveThemInRedisSet() {
        String countries = "countries";

        String countryOne = "Spain";
        String countryTwo = "Ireland";
        String countryThree = "Ireland";

        jedis.sadd(countries, countryOne);

        Set<String> countriesSet = jedis.smembers(countries);
        assertEquals(1, countriesSet.size());

        jedis.sadd(countries, countryTwo);
        countriesSet = jedis.smembers(countries);
        assertEquals(2, countriesSet.size());

        jedis.sadd(countries, countryThree);
        countriesSet = jedis.smembers(countries);
        assertEquals(2, countriesSet.size());

        boolean exists = jedis.sismember(countries, countryThree);
        assertTrue(exists);
    }

    @Test
    public void givenObjectFields_thenSaveThemInRedisHash() {
        String key = "user#1";

        String field = "name";
        String value = "William";

        String field2 = "job";
        String value2 = "politician";

        jedis.hset(key, field, value);
        jedis.hset(key, field2, value2);

        String value3 = jedis.hget(key, field);
        assertEquals(value, value3);

        Map<String, String> fields = jedis.hgetAll(key);
        String value4 = fields.get(field2);
        assertEquals(value2, value4);
    }

    @Test
    public void givenARanking_thenSaveItInRedisSortedSet() {
        String key = "ranking";

        Map<String, Double> scores = new HashMap<>();

        scores.put("PlayerOne", 3000.0);
        scores.put("PlayerTwo", 1500.0);
        scores.put("PlayerThree", 8200.0);

        scores.entrySet().forEach(playerScore -> {
            jedis.zadd(key, playerScore.getValue(), playerScore.getKey());
        });

        Set<String> players = jedis.zrevrange(key, 0, 1);
        assertEquals("PlayerThree", players.iterator().next());

        long rank = jedis.zrevrank(key, "PlayerOne");
        assertEquals(1, rank);
    }

    @Test
    public void givenMultipleOperationsThatNeedToBeExecutedAtomically_thenWrapThemInATransaction() {
        String friendsPrefix = "friends#";

        String userOneId = "4352523";
        String userTwoId = "5552321";

        Transaction t = jedis.multi();
        t.sadd(friendsPrefix + userOneId, userTwoId);
        t.sadd(friendsPrefix + userTwoId, userOneId);
        t.exec();

        boolean exists = jedis.sismember(friendsPrefix + userOneId, userTwoId);
        assertTrue(exists);

        exists = jedis.sismember(friendsPrefix + userTwoId, userOneId);
        assertTrue(exists);
    }

    @Test
    public void givenMultipleIndependentOperations_whenNetworkOptimizationIsImportant_thenWrapThemInAPipeline() {
        String userOneId = "4352523";
        String userTwoId = "4849888";

        Pipeline p = jedis.pipelined();
        p.sadd("searched#" + userOneId, "paris");
        p.zadd("ranking", 126, userOneId);
        p.zadd("ranking", 325, userTwoId);
        Response<Boolean> pipeExists = p.sismember("searched#" + userOneId, "paris");
        Response<Set<String>> pipeRanking = p.zrange("ranking", 0, -1);
        p.sync();

        assertTrue(pipeExists.get());
        assertEquals(2, pipeRanking.get().size());
    }

    /**
     * It is important to know that the way we have been dealing with our Jedis instance is naive.
     * In a real-world scenario, you do not want to use a single connection instance in a multi-threaded environment as a single connection instance is not thread-safe.
     *
     * Luckily enough we can easily create a pool of connections to Redis for us to reuse on demand,
     * a pool that is thread safe and reliable as long as you return the resource to the pool when you are done with it.
     */
    @Test
    public void givenAPoolConfiguration_thenCreateAJedisPool() {
        final JedisPoolConfig poolConfig = buildPoolConfig();

        try (JedisPool jedisPool = new JedisPool(poolConfig, "localhost", port); Jedis jedis = jedisPool.getResource()) {

            // do simple operation to verify that the Jedis resource is working
            // properly
            String key = "key";
            String value = "value";

            jedis.set(key, value);
            String value2 = jedis.get(key);

            assertEquals(value, value2);

            // flush Redis
            jedis.flushAll();
        }
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}