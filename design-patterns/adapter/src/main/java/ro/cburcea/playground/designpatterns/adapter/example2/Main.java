package ro.cburcea.playground.designpatterns.adapter.example2;

public class Main {

    public static void main(String[] args) {
        LoggerAPI logger = LoggerFactory.getLogger("log4j");
        logger.info("some message");
    }
}
