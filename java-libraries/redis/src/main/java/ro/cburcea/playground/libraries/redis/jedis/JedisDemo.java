package ro.cburcea.playground.libraries.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * docker run -p 6379:6379 --name redis-demo redis
 * RedisInsight: https://redislabs.com/redisinsight/
 */
public class JedisDemo {

    public static void main(String[] args) {
        //jedis connection; it's not thread-safe
        Jedis jedis = new Jedis();

        stringsDemo(jedis);
        listsDemo(jedis);
        setsDemo(jedis);
        hashesDemo(jedis);
        sortedSetsDemo(jedis);
    }

    private static void sortedSetsDemo(Jedis jedis) {
        System.out.println("SORTED_SETS");
        Map<String, Double> scores = new HashMap<>();

        scores.put("PlayerOne", 3000.0);
        scores.put("PlayerTwo", 1500.0);
        scores.put("PlayerThree", 8200.0);

        scores.forEach((key, value) -> jedis.zadd("ranking", value, key));

        String player = jedis.zrevrange("ranking", 0, 1).iterator().next();
        long rank = jedis.zrevrank("ranking", "PlayerOne");
        System.out.println(player);
        System.out.println(rank);
    }

    private static void hashesDemo(Jedis jedis) {
        System.out.println("HASHES");
        jedis.hset("user#1", "name", "Peter");
        jedis.hset("user#1", "job", "politician");

        String name = jedis.hget("user#1", "name");

        Map<String, String> fields = jedis.hgetAll("user#1");
        String job = fields.get("job");
        System.out.println(fields);
        System.out.println();
    }

    private static void setsDemo(Jedis jedis) {
        System.out.println("SETS");
        jedis.sadd("nicknames", "nickname#1");
        jedis.sadd("nicknames", "nickname#2");
        jedis.sadd("nicknames", "nickname#1");

        Set<String> nicknames = jedis.smembers("nicknames");
        boolean exists = jedis.sismember("nicknames", "nickname#1");
        System.out.println(nicknames);
        System.out.println(exists);
        System.out.println();
    }

    private static void listsDemo(Jedis jedis) {
        System.out.println("LISTS");
        jedis.lpush("list", "firstTask");
        jedis.lpush("list", "secondTask");

        System.out.println(jedis.rpop("list"));
        System.out.println(jedis.rpop("list"));
        System.out.println();
    }

    private static void stringsDemo(Jedis jedis) {
        System.out.println("STRING");
        jedis.set("events/city/rome", "32,15,223,828");
        String cachedResponse = jedis.get("events/city/rome");
        System.out.println(cachedResponse);
        System.out.println();
    }
}
