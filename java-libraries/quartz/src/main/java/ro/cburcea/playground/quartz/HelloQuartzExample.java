package ro.cburcea.playground.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .storeDurably(false)
                .requestRecovery()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)
                        .withRepeatCount(10))
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
