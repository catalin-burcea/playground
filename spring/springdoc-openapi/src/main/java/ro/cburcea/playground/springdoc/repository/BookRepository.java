package ro.cburcea.playground.springdoc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.springdoc.model.Book;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Repository
public class BookRepository {

    private Map<Long, Book> books = new HashMap<>();


    @PostConstruct
    public void init() {
        this.add(new Book(1, "title1", "author1"));
        this.add(new Book(2, "title2", "author2"));
        this.add(new Book(3, "title3", "author3"));
        this.add(new Book(4, "title4", "author4"));
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book add(Book book) {
        return books.put(book.getId(), book);
    }

    public Collection<Book> getBooks() {
        return books.values();
    }

    public Page<Book> getBooks(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();
        List<Book> result = books.values().stream().skip(toSkip).limit(pageable.getPageSize()).collect(toList());

        return new PageImpl<>(result, pageable, books.size());
    }

    public void delete(Long id) {
        books.remove(id);
    }
}