package ro.cburcea.playground.quartz.triggers;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ro.cburcea.playground.quartz.HelloJob;

import java.util.Date;
import java.util.TimeZone;

import static org.quartz.CronScheduleBuilder.cronSchedule;

public class CronTriggerQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(new Date())
                .forJob(job)
                .withSchedule(cronSchedule("0 42 10 ? * WED")
                        .inTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                        .withMisfireHandlingInstructionFireAndProceed())
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
