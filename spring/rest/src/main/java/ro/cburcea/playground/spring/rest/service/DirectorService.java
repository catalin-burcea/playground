package ro.cburcea.playground.spring.rest.service;


import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    private List<Director> directors = new ArrayList<>();

    @PostConstruct
    public void init() {

        for (int i = 1; i < 10; i++) {
            directors.add(new Director(i, "firstName" + i, "lastName" + i, 1950 + i,
                    Arrays.asList(
                            new Movie(i * i, "Title" + i, 1980 + i, i),
                            new Movie(i * i + 1, "Title" + i + 1, 1980 + i, i)
                    )));
        }

    }

    public List<Director> findAll() {
        return directors;
    }

    public List<Movie> findAllMoviesByDirectorId(int id) {
        return directors
                .stream()
                .filter(dir -> id == dir.getId())
                .map(Director::getMovies)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Optional<Director> findById(int id) {
        return directors
                .stream()
                .filter(dir -> id == dir.getId())
                .findFirst();
    }

    public void insert(Director director) {
        directors.add(director);
    }

}
