package ro.cburcea.playground.scheduling.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class ScheduledAsyncTasks {

    /**
     * If we want to support parallel behavior in scheduled tasks, we need to add the @Async annotation:
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println("Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }

    @Async("threadPoolTaskExecutor")
    @Scheduled(fixedRate = 1000)
    public void scheduleAsyncFixedRateTaskOverrideExecutor() throws InterruptedException {
        System.out.println("Fixed rate task async - override executor - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }

    @Async
    public Future<String> asyncMethodWithReturnType() throws InterruptedException {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
        Thread.sleep(5000);
        return new AsyncResult<String>("asyncMethodWithReturnType");
    }

    @Async
    @Scheduled(fixedRate = 4000)
    public void asyncMethodWithExceptions() throws Exception {
        throw new Exception("Throw message from asynchronous method. ");
    }

}