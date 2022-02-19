package ro.cburcea.playground.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.spring.rest.assembler.DirectorAssembler;
import ro.cburcea.playground.spring.rest.assembler.MovieAssembler;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.dtos.DirectorDto;
import ro.cburcea.playground.spring.rest.dtos.MovieDto;
import ro.cburcea.playground.spring.rest.service.DirectorService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/directors")
public class HateoasDirectorController {

    private static final String APPLICATION_HAL_JSON = "application/hal+json";

    @Autowired
    private DirectorService directorService;

    @Autowired
    private PagedResourcesAssembler<Director> pagedResourcesAssembler;

    @Autowired
    private DirectorAssembler directorAssembler;

    @Autowired
    private MovieAssembler movieAssembler;

    @GetMapping(produces = APPLICATION_HAL_JSON)
    public ResponseEntity<PagedModel<DirectorDto>> getAllDirectors(@RequestParam(required = false) String firstName,
                                                                   Pageable pageable) {
        Page<Director> directors;
        if (StringUtils.isEmpty(firstName)) {
            directors = directorService.findAll(pageable);
        } else {
            directors = directorService.findAllByFirstName(firstName, pageable);
        }
        Link allDirectorsLink = linkTo(methodOn(HateoasDirectorController.class).getAllDirectors(null, pageable)).withSelfRel();
        PagedModel<DirectorDto> directorModel = pagedResourcesAssembler.toModel(directors, directorAssembler, allDirectorsLink);
        return ResponseEntity.ok(directorModel);
    }


    @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON)
    public ResponseEntity<DirectorDto> getDirectorById(@PathVariable Long id) {
        return directorService.findById(id)
                .map(directorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    @GetMapping(value = "/{id}/movies", produces = APPLICATION_HAL_JSON)
    public ResponseEntity<CollectionModel<MovieDto>> getDirectorMovies(@PathVariable Long id) {
        final List<Movie> movies = directorService.findAllMoviesByDirectorId(id);

        Link directorMoviesLink = linkTo(methodOn(HateoasDirectorController.class).getDirectorMovies(id)).withSelfRel();
        Link directorsLink = linkTo(methodOn(HateoasDirectorController.class).getAllDirectors(null, null)).withRel("directors");

        CollectionModel<MovieDto> movieDtos = movieAssembler.toCollectionModel(movies);
        return ResponseEntity.ok(new CollectionModel<>(movieDtos, Arrays.asList(directorMoviesLink, directorsLink)));
    }

}
