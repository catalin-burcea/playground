package ro.cburcea.playground.spring.rest.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.repository.DirectorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Page<Director> findAll(Pageable pageable) {
        return directorRepository.findAll(pageable);
    }

    public Page<Director> findAllByFirstName(String firstName, Pageable pageable) {
        return directorRepository.findByFirstName(firstName, pageable);
    }

    public List<Movie> findAllMoviesByDirectorId(Long id) {
        Optional<Director> director = directorRepository.findById(id);
        if (director.isPresent()) {
            return director.get().getMovies();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

    public void insert(Director director) {
        directorRepository.save(director);
    }

    public void update(Director director) {
        directorRepository.save(director);
    }

}
