package ro.cburcea.playground.spring.rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.rest.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
