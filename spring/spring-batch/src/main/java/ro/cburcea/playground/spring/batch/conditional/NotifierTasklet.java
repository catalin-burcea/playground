package ro.cburcea.playground.spring.batch.conditional;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class NotifierTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        System.err.println("[" + chunkContext.getStepContext().getJobName() + "] contains positive data!!");
        return RepeatStatus.FINISHED;
    }
}