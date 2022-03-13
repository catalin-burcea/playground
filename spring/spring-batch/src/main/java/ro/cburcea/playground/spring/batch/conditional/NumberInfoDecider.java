package ro.cburcea.playground.spring.batch.conditional;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class NumberInfoDecider implements JobExecutionDecider {

    public static final String NOTIFY = "NOTIFY";
    public static final String QUIET = "QUIET";

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (shouldNotify()) {
            return new FlowExecutionStatus(NOTIFY);
        }
        return new FlowExecutionStatus(QUIET);

    }

    private boolean shouldNotify() {
        return true;
    }
}