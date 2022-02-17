package ro.cburcea.playground.spring.rest.dtos;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Relation(itemRelation = "director", collectionRelation = "directors")
public class DirectorDto extends RepresentationModel<DirectorDto> {
    
    private int id;
    private String firstname;
    private String lastname;
    private int year;

    public DirectorDto() {
    }

    public DirectorDto(int id, String firstname, String lastname, int year, Set<MovieDto> movies) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}