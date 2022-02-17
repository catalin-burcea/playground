package ro.cburcea.playground.spring.rest.domain;


import java.util.List;

public class Director {
    private int id;
    private String firstname;
    private String lastname;
    private int year;
    private List<Movie> movies;

    public Director() {
    }

    public Director(int id, String firstname, String lastname, int year, List<Movie> movies) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.year = year;
        this.movies = movies;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}