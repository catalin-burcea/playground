package ro.cburcea.playground.spring.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppStartupRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    void init() {
        customerRepository.save(new Customer("John", "Doe", 22));
        customerRepository.save(new Customer("John", "Smith", 22));
        customerRepository.save(new Customer("John", "Williams", 22));
        customerRepository.save(new Customer("John", "Cena", 22));
        customerRepository.save(new Customer("John", "Isner", 22));
        customerRepository.save(new Customer("John", "Walker", 22));


        customerRepository.findAll(PageRequest.of(1, 2)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingJpql(PageRequest.of(1, 3)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingNativeQuery(PageRequest.of(0, 2)).forEach(customer -> LOG.info(customer.toString()));
    }
}
