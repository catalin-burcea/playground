package ro.cburcea.playground.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello! HelloJob is executing.");

        //Quartz best practices: all exceptions should be wrapped in a JobExecutionException
    }
}
