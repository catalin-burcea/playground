package ro.cburcea.playground.spring.rest.dtos;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "director", collectionRelation = "directors")
public class DirectorDto extends RepresentationModel<DirectorDto> {
    
    private Long id;
    private String firstName;
    private String lastName;
    private int year;

    public DirectorDto() {
    }

    public DirectorDto(Long id, String firstName, String lastName, int year) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}