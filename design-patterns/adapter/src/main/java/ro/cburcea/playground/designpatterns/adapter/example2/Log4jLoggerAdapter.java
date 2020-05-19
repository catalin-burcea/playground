package ro.cburcea.playground.designpatterns.adapter.example2;

import org.apache.log4j.Logger;

public class Log4jLoggerAdapter implements LoggerAPI {

    //adaptee
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
