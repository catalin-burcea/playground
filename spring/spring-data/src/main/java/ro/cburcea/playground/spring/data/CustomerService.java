package ro.cburcea.playground.spring.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    void init() {
        customerRepository.save(new Customer("John", "Doe", 22));
        customerRepository.save(new Customer("John", "Smith", 23));
        customerRepository.save(new Customer("John", "Williams", 24));
        customerRepository.save(new Customer("John", "Cena", 25));
        customerRepository.save(new Customer("John", "Isner", 26));
        customerRepository.save(new Customer("John", "Walker", 27));


        customerRepository.findAll(PageRequest.of(1, 2)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingJpql(PageRequest.of(1, 3)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingNativeQuery(PageRequest.of(0, 2)).forEach(customer -> LOG.info(customer.toString()));

        Customer customer1 = customerRepository.findCustomerByFirstNameAndLastName("John", "Cena");
        LOG.info("customer1 {}", customer1.toString());
        Customer customer2 = customerRepository.findCustomerByFirstNameAndAge("John", 23);
        LOG.info("customer2 {}", customer2.toString());
        customerRepository.findUserByNameList(Arrays.asList("Smith", "Cena", "Walker")).forEach(customer -> LOG.info(customer.toString()));

    }

    @Transactional
    public void insertCustomer() {
        customerRepository.insertCustomer(100, "Jason", "Statham", 50);
        Customer customer = customerRepository.findCustomerByFirstNameAndLastName("Jason", "Statham");
        LOG.info("insertCustomer {}", customer.toString());
    }


    @Transactional
    public void updateCustomer() {
        customerRepository.updateCustomerFirstName("Walker", "Paul");
        final Customer customer = customerRepository.findCustomerByFirstNameAndLastName("Paul", "Walker");
        LOG.info("updateCustomer {}", customer.toString());
    }
}
