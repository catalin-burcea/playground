package ro.cburcea.playground.quartz.datamap;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * When a trigger fires, the JobDetail (instance definition) it is associated to is loaded,
 * and the job class it refers to is instantiated via the JobFactory configured on the Scheduler.
 * The default JobFactory simply calls newInstance() on the job class, then attempts to call setter methods on the class that match the names of keys within the JobDataMap.
 * You may want to create your own implementation of JobFactory
 * to accomplish things such as having your application’s IoC or DI container produce/initialize the job instance.
 */
public class DataMapQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(DataMapJob2.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
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
