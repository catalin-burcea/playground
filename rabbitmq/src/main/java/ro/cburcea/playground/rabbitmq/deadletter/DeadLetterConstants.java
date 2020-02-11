package ro.cburcea.playground.rabbitmq.deadletter;

public interface DeadLetterConstants {
    String EXCHANGE_NAME = "topic_logs";
    String DEAD_LETTER_EXCHANGE_NAME = "dead_letter_logs";
}
