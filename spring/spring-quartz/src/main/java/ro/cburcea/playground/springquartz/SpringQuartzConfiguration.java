package ro.cburcea.playground.springquartz;

import org.quartz.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.sql.DataSource;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class SpringQuartzConfiguration {

    // JobDetail bean: Quartz style
//    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder.newJob().ofType(SampleJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Sample Job service...")
                .build();
    }

    // JobDetail bean: Spring style
    @Bean
    public JobDetailFactoryBean springJobDetail2() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SampleJob.class);
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setGroup("group1");
        jobDetailFactory.setRequestsRecovery(true);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("key1", "val1");
        jobDataMap.put("key2", "val2");
        jobDetailFactory.setJobDataMap(jobDataMap);

        return jobDetailFactory;
    }

    // Trigger bean: Quartz style
//    @Bean
    public Trigger quartzTrigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Trigger")
                .withDescription("Sample trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
                .build();
    }

    // Trigger bean: Spring style
    @Bean
    public SimpleTriggerFactoryBean springTrigger(JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setRepeatInterval(1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("trigger1");
        trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);
        trigger.setGroup("group1");

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("key1", "val1");
        jobDataMap.put("key2", "val2");
        trigger.setJobDataMap(jobDataMap);

        return trigger;
    }

//    @Bean
//    @QuartzDataSource
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setGlobalJobListeners(new CustomJobListener());
        schedulerFactory.setGlobalTriggerListeners(new CustomTriggerListener());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
//        schedulerFactory.setDataSource(quartzDataSource);
        return schedulerFactory;
    }

}
