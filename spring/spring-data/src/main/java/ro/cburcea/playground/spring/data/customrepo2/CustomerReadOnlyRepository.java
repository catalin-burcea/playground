package ro.cburcea.playground.spring.data.customrepo2;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.cburcea.playground.spring.data.Customer;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CustomerReadOnlyRepository extends ReadOnlyRepository<Customer, Long> {
    List<Customer> findByEmail(String email);

    List<Customer> findByLastName(String lastName);
}