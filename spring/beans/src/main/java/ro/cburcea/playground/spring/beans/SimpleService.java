package ro.cburcea.playground.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

@Component
public class SimpleService {

    @Resource(name = "simplePojo1")
    SimplePojo resource1;

    @Resource
    @Qualifier("simplePojo1")
    SimplePojo resource2;

    @Autowired
    @Qualifier(value = "simplePojo2")
    SimplePojo autowired;

    @Inject
    @Qualifier(value = "simplePojo3")
    SimplePojo inject1;

    @Inject
    @Named("simplePojo3")
    SimplePojo inject2;

    @PostConstruct
    public void postConstruct() {
        System.out.println("resource1=" + resource1.getPojo());
        System.out.println("resource2=" + resource2.getPojo());
        System.out.println("autowired=" + autowired.getPojo());
        System.out.println("inject1=" + inject1.getPojo());
        System.out.println("inject2=" + inject2.getPojo());
    }
}
