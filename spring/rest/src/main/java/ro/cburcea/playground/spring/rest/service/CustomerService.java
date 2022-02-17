package ro.cburcea.playground.spring.rest.service;


import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.domain.Order;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();

    @PostConstruct
    public void init() {

        for (int i = 1; i < 10; i++) {
            customers.add(new Customer(i, "firstName" + i, "lastName" + i, i,
                    Arrays.asList(
                            new Order(i * i, i * i, i + i),
                            new Order(i + 1, i * i, i + i)
                    )));
        }

    }

    public List<Customer> findAll() {
        return customers;
    }

    public List<Order> findAllOrdersByCustomerId(int id) {
        return customers
                .stream()
                .filter(cust -> id == cust.getId())
                .map(Customer::getOrders)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(int id) {
        return customers
                .stream()
                .filter(cust -> id == cust.getId())
                .findFirst();
    }

    public Customer insert(Customer customer) {
        customers.add(customer);
        return customer;
    }

    public void update(Customer oldCustomer, Customer updatedCustomer) {
        customers.set(customers.indexOf(oldCustomer), updatedCustomer);
    }


}
