package ro.cburcea.playground.quartz.triggers;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ro.cburcea.playground.quartz.HelloJob;

import java.util.Date;

import static org.quartz.DateBuilder.dateOf;

public class SimpleTriggerQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(new Date())
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount())
                .endAt(dateOf(22, 0, 0))
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
