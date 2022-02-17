package ro.cburcea.playground.spring.rest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.domain.Order;
import ro.cburcea.playground.spring.rest.repository.CustomerRepository;
import ro.cburcea.playground.spring.rest.repository.OrderRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {

        for (int i = 1; i < 20; i++) {
            Customer customer = new Customer("firstName" + i, "lastName" + i, i);
            customerRepository.save(customer);
            final Order order1 = new Order(i * i, i + i);
            final Order order2 = new Order(i * i + 1, i + i + 1);
            orderRepository.save(order1);
            orderRepository.save(order2);
            customer.addOrder(order1);
            customer.addOrder(order2);
            customerRepository.save(customer);
        }

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
