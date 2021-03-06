package name.saifmahmud.demo.http.controllers;

import name.saifmahmud.demo.entities.Book;
import name.saifmahmud.demo.entities.BookMeta;
import name.saifmahmud.demo.entities.User;
import name.saifmahmud.demo.http.dtos.BookDto;
import name.saifmahmud.demo.repositories.BookRepository;
import name.saifmahmud.demo.services.EmailService;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookRepository repo;

    private final ModelMapper mapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final EmailService email;

    private final EntityManager entityManager;

    @Autowired
    public BookController(BookRepository repo, EmailService email, EntityManager entityManager) {
        this.repo = repo;
        this.email = email;
        this.entityManager = entityManager;

        mapper.addMappings(new PropertyMap<BookDto, Book>() {
            @Override
            protected void configure() {
                skip().setMeta(null);
            }
        });
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> list() {
        logger.info("Listing all books");
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("search")
    public ResponseEntity<Iterable<Book>> search(@RequestParam String q) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Book> result = searchSession.search(Book.class)
                .where(f -> f.match()
                        .fields("title", "author")
                        .matching(q))
                .fetchAll();

        Iterable<Book> books = result.hits();

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookDto data) {
        logger.info("Creating new book");
        Book book = mapper.map(data, Book.class);

        repo.save(book);
        book.setMeta(mapper.map(data.getMeta(), BookMeta.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(book));
    }

    @GetMapping("{book}")
    public ResponseEntity<Book> show(@PathVariable Book book) {
        logger.info("Showing book info");
        return ResponseEntity.ok(book);
    }

    @PutMapping("{book}")
    public ResponseEntity<Book> update(@Valid @RequestBody BookDto data, @PathVariable Book book) {
        logger.info("Updating book info");

        mapper.map(data, book);
        mapper.map(data.getMeta(), book.getMeta());

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
