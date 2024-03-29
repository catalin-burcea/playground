package ro.cburcea.playground.springdoc.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Book {

    private long id;

    public Book() {
    }

    public Book(long id, @NotBlank @Size(min = 0, max = 20) String title, @NotBlank @Size(min = 0, max = 30) String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @NotBlank
    @Size(min = 0, max = 20)
    private String title;

    @NotBlank
    @Size(min = 0, max = 30)
    private String author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}