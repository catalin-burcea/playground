package ro.cburcea.playground.spring.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private CustomerService customerService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEventListener2() {
        LOG.info("EXECUTING : eventListener");
        customerService.updateCustomer();
        customerService.insertCustomer();
    }
}
