package ro.cburcea.playground.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ConcurrentQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(ConcurrentJob.class)
                .storeDurably()
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1) // 0 = concurrently?!
                        .withRepeatCount(3))
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger2", "group1")
                .startNow()
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1) // 0 = concurrently?!
                        .withRepeatCount(3))
                .build();

        scheduler.addJob(job, false);
        scheduler.scheduleJob(trigger);
        scheduler.scheduleJob(trigger2);
    }
}
