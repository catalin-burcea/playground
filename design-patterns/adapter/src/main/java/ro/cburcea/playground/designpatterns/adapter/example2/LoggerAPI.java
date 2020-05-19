package ro.cburcea.playground.designpatterns.adapter.example2;

/**
 * adapter/client interface
 */
public interface LoggerAPI {

    public void info(String message);

    public void error(String message);

    public void warn(String message);
}
