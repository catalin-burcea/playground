package ro.cburcea.playground.spring.amqp.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

/**
 * check whether an exception should be considered fatal. If so, the failed message will be rejected.
 * <p>
 * By default these exceptions are fatal:
 * <p>
 * MessageConversionException
 * MessageConversionException
 * MethodArgumentNotValidException
 * MethodArgumentTypeMismatchException
 * NoSuchMethodException
 * ClassCastException
 */
public class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFatalExceptionStrategy.class);

    @Override
    public boolean isFatal(Throwable t) {
        if (t.getCause() instanceof BusinessException) {
            return false;
        }
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
            LOGGER.error("Failed to process inbound message from queue "
                    + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                    + "; failed message: " + lefe.getFailedMessage(), t);
        }
        return super.isFatal(t);
    }

}

