package ro.cburcea.playground.spring.rest.service;


import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.domain.Order;
import ro.cburcea.playground.spring.rest.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Order> findAllOrdersByCustomerId(Long id) {
        return customerRepository.findAll()
                .stream()
                .filter(cust -> id.equals(cust.getId()))
                .map(Customer::getOrders)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer insert(Customer customer) {
        return customerRepository.save(customer);
    }

    public void update(Customer updatedCustomer) {
        customerRepository.save(updatedCustomer);
    }

}
