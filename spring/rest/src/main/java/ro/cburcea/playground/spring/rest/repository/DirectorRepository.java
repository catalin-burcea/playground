package ro.cburcea.playground.spring.rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.rest.domain.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

}
