package ro.cburcea.playground.spring.data.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.cburcea.playground.spring.data.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/*
    The class is annotated with @Repository to enable exception translation from JPA exceptions to Spring’s DataAccessException hierarchy
    The @Transactional annotation causes some performance optimizations inside the persistence provider as well as on the database level.
 */
@Repository
@Transactional(readOnly = true)
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> findCustomer() {
        return entityManager.createNativeQuery("test").getResultList();
    }
}
