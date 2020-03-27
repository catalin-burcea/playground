package ro.cburcea.playground.quartz.listeners;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import java.util.logging.Logger;

public class TriggerListener extends TriggerListenerSupport {

    private static final Logger LOGGER = Logger.getLogger(TriggerListener.class.getName());

    @Override
    public String getName() {
        return "trigger-listener1";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        LOGGER.info("trigger " + trigger.getKey().getName() + " was fired; job: " + context.getJobDetail().getKey().getName());
        super.triggerFired(trigger, context);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return super.vetoJobExecution(trigger, context);
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        LOGGER.info("trigger " + trigger.getKey().getName() + " was misfired; job: ");
        super.triggerMisfired(trigger);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        LOGGER.info("trigger " + trigger.getKey().getName() + " was finished");
        super.triggerComplete(trigger, context, triggerInstructionCode);
    }
}
