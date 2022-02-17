package ro.cburcea.playground.spring.rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.rest.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
