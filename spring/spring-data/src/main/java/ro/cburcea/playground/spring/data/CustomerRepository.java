package ro.cburcea.playground.spring.data;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c from Customer c")
    List<Customer> findAllCustomersWithPaginationUsingJpql(Pageable pageable);

    @Query(value = "SELECT * FROM customers ORDER BY id desc",
            countQuery = "SELECT count(*) FROM customers",
            nativeQuery = true)
    Page<Customer> findAllCustomersWithPaginationUsingNativeQuery(Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.firstName = ?1 and c.lastName = ?2")
    Customer findCustomerByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstName and c.age = :age")
    Customer findCustomerByFirstNameAndAge(@Param("firstName") String firstName, @Param("age") Integer age);

    @Query(value = "SELECT c FROM Customer c WHERE c.lastName IN :names")
    List<Customer> findUserByNameList(@Param("names") Collection<String> names);

}
