package ro.cburcea.playground.quartz.datamap;

import org.quartz.*;

public class DataMapJob implements Job {

    public DataMapJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.out.println("Instance " + key + " of DataMapJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}