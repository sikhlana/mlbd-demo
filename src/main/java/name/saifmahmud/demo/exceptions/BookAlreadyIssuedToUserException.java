package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyIssuedToUserException extends ResponseStatusException {
    public BookAlreadyIssuedToUserException() {
        super(HttpStatus.CONFLICT, "book.already_issued");
    }
}
