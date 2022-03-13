package ro.cburcea.playground.spring.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cburcea.playground.spring.data.customrepo.AnotherCustomerRepository;
import ro.cburcea.playground.spring.data.customrepo2.CustomerReadOnlyRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AnotherCustomerRepository anotherCustomerRepository;

    @Autowired
    private CustomerReadOnlyRepository customerReadOnlyRepository;

    @PostConstruct
    void init() {
        customerRepository.save(new Customer("John", "Doe", "a@a.com", 22));
        customerRepository.save(new Customer("John", "Smith", "b@b.com", 23));
        customerRepository.save(new Customer("John", "Cena", "c@c.com", 25));
        customerRepository.save(new Customer("John", "Isner", "d@d.com", 26));
        customerRepository.save(new Customer("John", "Walker", "e@e.com", 27));
        customerRepository.save(new Customer("John", "Williams", "f@f.com", 24));
    }

    public void findCustomersUsingQueryParams() {
        Customer customer1 = customerRepository.findCustomerByFirstNameAndLastName("John", "Cena");
        LOG.info("customer1 {}", customer1.toString());
        Customer customer2 = customerRepository.findCustomerByFirstNameAndAge("John", 23);
        LOG.info("customer2 {}", customer2.toString());
        customerRepository.findUserByNameList(Arrays.asList("Smith", "Cena", "Walker")).forEach(customer -> LOG.info(customer.toString()));
    }

    public void findCustomersWithPagination() {
        customerRepository.findAll(PageRequest.of(1, 2)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingJpql(PageRequest.of(1, 3)).forEach(customer -> LOG.info(customer.toString()));
        customerRepository.findAllCustomersWithPaginationUsingNativeQuery(PageRequest.of(0, 2)).forEach(customer -> LOG.info(customer.toString()));
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

    public void findCustomerByEmails() {
        final List<Customer> customer = anotherCustomerRepository.findCustomerByEmails(Arrays.asList("example@test.org", "test@test.com"));
        final List<Customer> customer2 = anotherCustomerRepository.findCustomerByEmails(Arrays.asList("example@test.org", "b@b.com"));
        LOG.info("findCustomerByEmails {}", customer.toString());
        LOG.info("findCustomerByEmails2 {}", customer2.toString());
    }

    public void findCustomerUsingCustomReadOnlyRepo() {
        final List<Customer> customers = customerReadOnlyRepository.findByLastName("Walker");
        final Optional<Customer> customer = customerReadOnlyRepository.findById(1L);
        LOG.info("findCustomerUsingCustomReadOnlyRepo {}", customers.toString());
        if(customer.isPresent()) {
            LOG.info("findCustomerUsingCustomReadOnlyRepo2 {}", customer.get().toString());
        }
    }
}
