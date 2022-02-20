package ro.cburcea.playground.spring.rest.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.cburcea.playground.spring.rest.controller.HateoasDirectorController;
import ro.cburcea.playground.spring.rest.controller.HateoasMovieController;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.dtos.MovieDto;
import ro.cburcea.playground.spring.rest.mapper.MovieMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieAssembler implements RepresentationModelAssembler<Movie, MovieDto> {

    @Override
    public MovieDto toModel(Movie movie) {
        MovieDto movieDto = MovieMapper.INSTANCE.mapToMovieDto(movie);
        movieDto.add(linkTo(methodOn(HateoasMovieController.class).getMovieById(movie.getId(), null)).withSelfRel());
        movieDto.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorById(movie.getDirector().getId())).withRel("director"));
        movieDto.add(linkTo(methodOn(HateoasMovieController.class).getAllMovies(null)).withRel("movies"));
        return movieDto;
    }

}
