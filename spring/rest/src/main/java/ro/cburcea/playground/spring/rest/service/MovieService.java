package ro.cburcea.playground.spring.rest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

}
