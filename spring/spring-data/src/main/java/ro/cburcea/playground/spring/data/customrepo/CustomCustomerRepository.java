package ro.cburcea.playground.spring.data.customrepo;

import ro.cburcea.playground.spring.data.Customer;

import java.util.List;

public interface CustomCustomerRepository {
    List<Customer> findCustomerByEmails(List<String> emails);
}