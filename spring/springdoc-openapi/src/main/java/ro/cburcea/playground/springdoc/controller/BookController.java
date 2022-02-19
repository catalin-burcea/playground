package ro.cburcea.playground.springdoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.springdoc.exception.BookNotFoundException;
import ro.cburcea.playground.springdoc.model.Book;
import ro.cburcea.playground.springdoc.repository.BookRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository repository;


    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)}
    )
    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        return repository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Operation(summary = "Get a list of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of books or empty list if there is no book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))})}
    )
    @GetMapping("/")
    public Page<Book> filterBooks(Pageable pageable) {
        return repository.getBooks(pageable);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void postBook(@NotNull @Valid @RequestBody final Book book) {
        repository.add(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable("id") final String id, @RequestBody final Book book) {
        return repository.add(book);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book patchBook(@PathVariable("id") final String id, @RequestBody final Book book) {
        return book;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable final long id) {
        repository.delete(id);
    }
}