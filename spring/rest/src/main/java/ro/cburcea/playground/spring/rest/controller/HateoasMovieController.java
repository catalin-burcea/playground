package ro.cburcea.playground.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.cburcea.playground.spring.rest.assembler.MovieAssembler;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.dtos.MovieDto;
import ro.cburcea.playground.spring.rest.service.MovieService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/movies")
public class HateoasMovieController {

    private static final String APPLICATION_HAL_JSON = "application/hal+json";

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieAssembler movieAssembler;

    @Autowired
    private PagedResourcesAssembler<Movie> pagedResourcesAssembler;

    @GetMapping(produces = APPLICATION_HAL_JSON)
    public ResponseEntity<PagedModel<MovieDto>> getAllMovies(Pageable pageable) {
        Page<Movie> movies = movieService.findAll(pageable);

        Link allMoviesLink = linkTo(methodOn(HateoasMovieController.class).getAllMovies(pageable)).withSelfRel();
        PagedModel<MovieDto> movieModel = pagedResourcesAssembler.toModel(movies, movieAssembler, allMoviesLink);
        return ResponseEntity.ok(movieModel);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON)
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movieAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
