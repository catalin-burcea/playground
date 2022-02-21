package ro.cburcea.playground.spring.data;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT u from Customer u")
    List<Customer> findAllCustomersWithPaginationUsingJpql(Pageable pageable);

    @Query(value = "SELECT * FROM customers ORDER BY id desc",
            countQuery = "SELECT count(*) FROM customers",
            nativeQuery = true)
    Page<Customer> findAllCustomersWithPaginationUsingNativeQuery(Pageable pageable);

}
