package ro.cburcea.playground.spring.amqp.retry;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

public class CustomRejectAndDontRequeueRecoverer extends RejectAndDontRequeueRecoverer {
    private Runnable observer;

    @Override
    public void recover(Message message, Throwable cause) {
        if (observer != null) {
            observer.run();
        }

        super.recover(message, cause);
    }

    void setObserver(Runnable observer) {
        this.observer = observer;
    }
}