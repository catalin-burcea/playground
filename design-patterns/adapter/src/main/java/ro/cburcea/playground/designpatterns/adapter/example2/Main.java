package ro.cburcea.playground.designpatterns.adapter.example2;

public class Main {

    public static void main(String[] args) {
        LoggerAPI logger = LoggerFactory.getLogger("jul");
        logger.info("some message");
    }
}
