package ro.cburcea.playground.spring.data.customrepo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.data.Customer;

@Repository
public interface AnotherCustomerRepository extends JpaRepository<Customer, Long>, CustomCustomerRepository {

}
