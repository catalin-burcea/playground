package ro.cburcea.playground.quartz.listeners;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

import java.util.logging.Logger;

public class JobListener extends JobListenerSupport {

    private static final Logger LOGGER = Logger.getLogger(JobListener.class.getName());

    @Override
    public String getName() {
        return "job-listener1";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        LOGGER.info("job to be executed: " + context.getJobDetail().getKey().getName());
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        LOGGER.info("executed job: " + context.getJobDetail().getKey().getName());
        super.jobWasExecuted(context, jobException);
    }
}
