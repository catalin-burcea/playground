package ro.cburcea.playground.designpatterns.adapter.example2;

import java.lang.reflect.Constructor;

public class LoggerFactory {

    private LoggerFactory() {
    }

    public static LoggerAPI getLogger(String name) {
        if ("jul".equalsIgnoreCase(name)) {
            return new JULLoggerAdapter();
        }
        return new Log4jLoggerAdapter();
    }

    //factory
    public static LoggerAPI getLoggerWithReflection(String loggerAdapterClassName) {
        LoggerAPI logger = null;
        try {
            Constructor<?> c = Class.forName(loggerAdapterClassName).getConstructor(String.class);
            logger = (LoggerAPI) c.newInstance(loggerAdapterClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }
}
