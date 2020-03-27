package ro.cburcea.playground.springquartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SampleJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("executing job: " + context.getJobDetail().getKey().getName());
    }
}
