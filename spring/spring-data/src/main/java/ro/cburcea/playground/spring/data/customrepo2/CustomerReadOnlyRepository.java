package ro.cburcea.playground.spring.data.customrepo2;

import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.data.Customer;

import java.util.List;

@Repository
public interface CustomerReadOnlyRepository extends ReadOnlyRepository<Customer, Long> {
    List<Customer> findByEmail(String email);

    List<Customer> findByLastName(String lastName);
}