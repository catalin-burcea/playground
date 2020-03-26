package ro.cburcea.playground.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    /**
     * In this case, the duration between the end of last execution and the start of next execution is fixed.
     * The task always waits until the previous one is finished.
     */
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed delay task - " + now);
        Thread.sleep(2000);
    }

    /**
     * This option should be used when each execution of the task is independent.
     * <p>
     * Note that scheduled tasks don't run in parallel by default. So even if we used fixedRate,
     * the next task won't be invoked until the previous one is done.
     */
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() throws InterruptedException {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed rate task - " + now);
        Thread.sleep(2000);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed rate task with one second initial delay - " + now);
    }

    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks using cron jobs - " + now);
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleTaskWithFixedDelayUsingProperties() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks with fixed delay using properties - " + now);
    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleTaskWithFixedRateUsingProperties() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks with fixed rate using properties - " + now);
    }

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTaskWithCronExpressionUsingProperties() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks with cron expression using properties - " + now);
    }

}
