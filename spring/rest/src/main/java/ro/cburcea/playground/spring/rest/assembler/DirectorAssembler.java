package ro.cburcea.playground.spring.rest.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.cburcea.playground.spring.rest.controller.HateoasDirectorController;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.dtos.DirectorDto;
import ro.cburcea.playground.spring.rest.mapper.DirectorMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorAssembler implements RepresentationModelAssembler<Director, DirectorDto> {

    @Override
    public DirectorDto toModel(Director director) {
        DirectorDto directorDto = DirectorMapper.INSTANCE.mapToDirectorDto(director);
        directorDto.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorById(director.getId())).withSelfRel());
        directorDto.add(linkTo(methodOn(HateoasDirectorController.class).getDirectorMovies(director.getId())).withRel("directorMovies"));
        directorDto.add(linkTo(methodOn((HateoasDirectorController.class)).getAllDirectors(null, 0, 3, null)).withRel("directors"));
        return directorDto;
    }

}
