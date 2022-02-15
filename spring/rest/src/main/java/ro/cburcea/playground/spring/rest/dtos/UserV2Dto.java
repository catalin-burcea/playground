package ro.cburcea.playground.spring.rest.dtos;

import java.util.List;


public class UserV2Dto {

    private int id;
    private String name;
    private int age;
    private List<String> sports;

    public UserV2Dto() {
    }

    public UserV2Dto(int id, String name, int age, List<String> sports) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sports = sports;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }
}
