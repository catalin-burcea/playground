package ro.cburcea.playground.quartz.listeners;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ro.cburcea.playground.quartz.HelloJob;

import static org.quartz.impl.matchers.EverythingMatcher.allJobs;
import static org.quartz.impl.matchers.EverythingMatcher.allTriggers;

public class EventsAndListenersQuartzExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .build();

        JobDetail job2 = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob2", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(3))
                .build();

        JobListener jobListener = new JobListener();
//        scheduler.getListenerManager().addJobListener(jobListener, KeyMatcher.keyEquals(new JobKey("myJob", "group1")));
//        scheduler.getListenerManager().addJobListener(jobListener, jobGroupEquals("group1"));
//        scheduler.getListenerManager().addJobListener(jobListener, or(jobGroupEquals("group1"), jobGroupEquals("group2")));
        scheduler.getListenerManager().addJobListener(jobListener, allJobs());

        TriggerListener triggerListener = new TriggerListener();
//        scheduler.getListenerManager().addTriggerListener(triggerListener, KeyMatcher.keyEquals(new TriggerKey("myTrigger", "group1")));
//        scheduler.getListenerManager().addTriggerListener(triggerListener, triggerGroupEquals("group1"));
//        scheduler.getListenerManager().addTriggerListener(triggerListener, or(triggerGroupEquals("group1"), triggerGroupEquals("group2")));
        scheduler.getListenerManager().addTriggerListener(triggerListener, allTriggers());
        scheduler.scheduleJob(job, trigger);

        SchedulerListener schedulerListener  = new CustomSchedulerListener();
        scheduler.getListenerManager().addSchedulerListener(schedulerListener);
    }
}
