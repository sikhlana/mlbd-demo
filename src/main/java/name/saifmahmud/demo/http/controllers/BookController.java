package name.saifmahmud.demo.http.controllers;

import name.saifmahmud.demo.entities.Book;
import name.saifmahmud.demo.entities.BookMeta;
import name.saifmahmud.demo.entities.User;
import name.saifmahmud.demo.http.dtos.BookDto;
import name.saifmahmud.demo.http.dtos.UserDto;
import name.saifmahmud.demo.repositories.BookRepository;
import name.saifmahmud.demo.services.EmailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookRepository repo;

    private final ModelMapper mapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final EmailService email;

    @Autowired
    public BookController(BookRepository repo, EmailService email) {
        this.repo = repo;
        this.email = email;
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> list() {
        logger.info("Listing all books");
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("search")
    public ResponseEntity<Iterable<Book>> search(@RequestParam @Nullable String title, @RequestParam @Nullable String author) {
        Iterable<Book> books = null;

        if (title != null) {
            logger.info("Searching books by title");
            books = repo.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            logger.info("Searching books by author");
            books = repo.findByAuthorContainingIgnoreCase(author);
        }

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookDto data) {
        logger.info("Creating new book");
        Book book = mapper.map(data, Book.class);
        BookMeta meta = book.getMeta();

        book.setMeta(null);
        repo.save(book);

        meta.setBook(book);
        book.setMeta(meta);

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(book));
    }

    @GetMapping("{book}")
    public ResponseEntity<Book> show(@PathVariable Book book) {
        logger.info("Showing book info");
        return ResponseEntity.ok(book);
    }

    @PutMapping("{book}")
    public ResponseEntity<Book> update(@Valid @RequestBody UserDto data, @PathVariable Book book) {
        logger.info("Updating book info");
        mapper.map(data, book);

        return ResponseEntity.ok(repo.save(book));
    }

    @DeleteMapping("{book}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Book book) {
        logger.info("Deleting book");
        repo.delete(book);
    }

    @GetMapping("{book}/users")
    public ResponseEntity<Set<User>> users(@PathVariable Book book) {
        logger.info("Listing all users holding book");
        return ResponseEntity.ok(book.getUsers());
    }

    @PutMapping("{book}/users/{user}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void issueToUser(@PathVariable Book book, @PathVariable User user) {
        logger.info("Issuing book to user");
        book.addUser(user);
        repo.save(book);

        email.sendSimpleMessage(
            user.getEmail(),
            "Book issued to you",
            String.format("We have successfully issued %s to you.", book.getTitle())
        );
    }
}
