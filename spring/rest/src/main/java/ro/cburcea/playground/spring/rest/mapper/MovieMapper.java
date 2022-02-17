package ro.cburcea.playground.spring.rest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.dtos.MovieDto;

import java.util.List;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto mapToMovieDto(Movie movie);

    Movie mapToMovie(MovieDto movieDto);

    List<MovieDto> mapToMoviesDto(List<Movie> movies);

}