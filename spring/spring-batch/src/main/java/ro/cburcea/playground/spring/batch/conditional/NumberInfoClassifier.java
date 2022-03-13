package ro.cburcea.playground.spring.batch.conditional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;

import static ro.cburcea.playground.spring.batch.conditional.NumberInfoDecider.NOTIFY;
import static ro.cburcea.playground.spring.batch.conditional.NumberInfoDecider.QUIET;


public class NumberInfoClassifier extends ItemListenerSupport<NumberInfo, Integer>
        implements ItemProcessor<NumberInfo, Integer> {

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        this.stepExecution.setExitStatus(new ExitStatus(QUIET));
    }

    @Override
    public void afterProcess(NumberInfo item, Integer result) {
        super.afterProcess(item, result);
        if (item.isPositive()) {
            stepExecution.setExitStatus(new ExitStatus(NOTIFY));
        }
    }

    @Override
    public Integer process(NumberInfo numberInfo) {
        return numberInfo.getNumber();
    }
}