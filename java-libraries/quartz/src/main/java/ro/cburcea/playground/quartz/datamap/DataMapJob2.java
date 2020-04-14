package ro.cburcea.playground.quartz.datamap;

import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;

public class DataMapJob2 implements Job {

    private String jobSays;
    private float myFloatValue;
    private ArrayList<Date> state;

    public DataMapJob2() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getMergedJobDataMap();  // Note the difference from the previous example

        System.out.println("Instance " + key + " of DataMapJob2 says: " + jobSays + ", and val is: " + myFloatValue);
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }

}