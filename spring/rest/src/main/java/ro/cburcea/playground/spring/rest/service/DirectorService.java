package ro.cburcea.playground.spring.rest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.repository.DirectorRepository;
import ro.cburcea.playground.spring.rest.repository.MovieRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void init() {

        for (int i = 1; i < 10; i++) {
            Director director = new Director("firstName" + i, "lastName" + i, 1950 + i);
            directorRepository.save(director);
            final Movie movie1 = new Movie("Title" + i, 1980 + i, i);
            final Movie movie2 = new Movie("Title" + i + 1, 1980 + i, i);
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            director.addMovie(movie1);
            director.addMovie(movie2);
            directorRepository.save(director);
        }

    }

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public List<Movie> findAllMoviesByDirectorId(Long id) {
        return directorRepository.findAll()
                .stream()
                .filter(dir -> id.equals(dir.getId()))
                .map(Director::getMovies)
                .flatMap(List::stream)
                .collect(Collectors.toList());
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
