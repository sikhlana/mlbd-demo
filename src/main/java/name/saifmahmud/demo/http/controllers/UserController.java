package name.saifmahmud.demo.http.controllers;

import name.saifmahmud.demo.entities.Book;
import name.saifmahmud.demo.entities.User;
import name.saifmahmud.demo.http.dtos.UserDto;
import name.saifmahmud.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository repo;

    private final ModelMapper mapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> list() {
        logger.info("Listing all users");
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserDto request) {
        logger.info("Creating new user");
        User user = mapper.map(request, User.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(user));
    }

    @GetMapping("{user}")
    public ResponseEntity<User> show(@PathVariable User user) {
        logger.info("Showing user info");
        return ResponseEntity.ok(user);
    }

    @PutMapping("{user}")
    public ResponseEntity<User> update(@Valid @RequestBody UserDto data, @PathVariable User user) {
        logger.info("Updating user info");
        mapper.map(data, user);

        return ResponseEntity.ok(repo.save(user));
    }

    @DeleteMapping("{user}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable User user) {
        logger.info("Deleting user");
        repo.delete(user);
    }

    @GetMapping("{user}/books")
    public ResponseEntity<Set<Book>> books(@PathVariable User user) {
        logger.info("Listing all books held by user");
        return ResponseEntity.ok(user.getBooks());
    }

    @DeleteMapping("{user}/books/{book}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitBook(@PathVariable User user, @PathVariable Book book) {
        logger.info("Submitting book by user");
        user.removeBook(book);
        repo.save(user);
    }
}
