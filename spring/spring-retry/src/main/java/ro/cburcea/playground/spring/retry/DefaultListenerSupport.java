package ro.cburcea.playground.spring.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;


public class DefaultListenerSupport implements RetryListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultListenerSupport.class);

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        LOGGER.info("onClose");
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
                                                 RetryCallback<T, E> callback, Throwable throwable) {
        LOGGER.info("onError: {}", throwable.getMessage());
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        LOGGER.info("onOpen");
        return true;
    }
}