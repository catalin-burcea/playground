package ro.cburcea.playground.spring.data.redis.pubsub;

public interface MessagePublisher {

    void publish(final String message);
}