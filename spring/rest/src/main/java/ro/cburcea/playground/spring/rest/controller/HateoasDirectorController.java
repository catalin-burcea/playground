package ro.cburcea.playground.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.dtos.DirectorDto;
import ro.cburcea.playground.spring.rest.dtos.MovieDto;
import ro.cburcea.playground.spring.rest.mapper.DirectorMapper;
import ro.cburcea.playground.spring.rest.mapper.MovieMapper;
import ro.cburcea.playground.spring.rest.service.DirectorService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HateoasDirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping(value = "/directors")
    public ResponseEntity<CollectionModel<DirectorDto>> getAllDirectors() {
        final List<Director> directors = directorService.findAll();
        List<DirectorDto> directorDtos = DirectorMapper.INSTANCE.mapToDirectorsDto(directors);

        directorDtos.forEach(director -> {
            director.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorById(director.getId())).withSelfRel());
            director.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorMovies(director.getId())).withRel("directorMovies"));
        });
        Link allDirectorsLink = linkTo(methodOn(HateoasDirectorController.class).getAllDirectors()).withSelfRel();

        return ResponseEntity.ok(new CollectionModel<>(directorDtos, allDirectorsLink));
    }


    @GetMapping(value = "/directors/{id}")
    public ResponseEntity<DirectorDto> getDirectorById(@PathVariable int id) {
        return directorService.findById(id)
                .map(DirectorMapper.INSTANCE::mapToDirectorDto)
                .map(director -> {
                    director.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorById(id)).withSelfRel());
                    director.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorMovies(id)).withRel("directorMovies"));
                    director.add(linkTo(methodOn((HateoasDirectorController.class)).getAllDirectors()).withRel("directors"));
                    return ResponseEntity.ok(director);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/directors/{id}/movies")
    public ResponseEntity<CollectionModel<MovieDto>> getDirectorMovies(@PathVariable int id) {
        final List<Movie> movies = directorService.findAllMoviesByDirectorId(id);
        List<MovieDto> movieDtos = MovieMapper.INSTANCE.mapToMoviesDto(movies);

        Link directorMoviesLink = linkTo(methodOn(HateoasDirectorController.class).getDirectorMovies(id)).withSelfRel();
        Link directorLink = linkTo(methodOn(HateoasDirectorController.class).getDirectorById(id)).withRel("director");
        Link directorsLink = linkTo(methodOn(HateoasDirectorController.class).getAllDirectors()).withRel("directors");

        return ResponseEntity.ok(new CollectionModel<>(movieDtos, Arrays.asList(directorMoviesLink, directorLink, directorsLink)));
    }

}
