package ro.cburcea.playground.springquartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

public class CustomTriggerListener extends TriggerListenerSupport {

    @Override
    public String getName() {
        return "trigger-listener1";
    }

    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    public void triggerMisfired(Trigger trigger) {

    }

    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
