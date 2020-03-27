package ro.cburcea.playground.quartz;

import org.quartz.*;

/**
 * @DisallowConcurrentExecution is an annotation that can be added to the Job class that tells Quartz not to execute multiple instances
 * of a given job definition (that refers to the given job class) concurrently.
 * In the example from the previous section, if “SalesReportJob” has this annotation,
 * then only one instance of “SalesReportForJoe” can execute at a given time, but it can execute concurrently with an instance of “SalesReportForMike”.
 * The constraint is based upon an instance definition (JobDetail), not on instances of the job class.
 *
 * @PersistJobDataAfterExecution is an annotation that can be added to the Job class that tells Quartz to update the stored copy of the JobDetail’s JobDataMap
 * after the execute() method completes successfully (without throwing an exception),
 * such that the next execution of the same job (JobDetail) receives the updated values rather than the originally stored values.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ConcurrentJob implements Job {

    public ConcurrentJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.out.println("Instance " + key + " of ConcurrentJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}