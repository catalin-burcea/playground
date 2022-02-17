package ro.cburcea.playground.spring.rest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.dtos.DirectorDto;

import java.util.List;

@Mapper
public interface DirectorMapper {

    DirectorMapper INSTANCE = Mappers.getMapper(DirectorMapper.class);

    DirectorDto mapToDirectorDto(Director director);

    Director mapToDirector(DirectorDto directorDto);

    List<DirectorDto> mapToDirectorsDto(List<Director> directors);


}