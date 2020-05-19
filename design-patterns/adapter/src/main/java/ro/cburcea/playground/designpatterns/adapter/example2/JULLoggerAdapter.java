package ro.cburcea.playground.designpatterns.adapter.example2;


import java.util.logging.Logger;

public class JULLoggerAdapter implements LoggerAPI {

    //adaptee
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }

    @Override
    public void warn(String message) {
        logger.warning(message);
    }
}
