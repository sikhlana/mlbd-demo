package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotIssuedToUserException extends ResponseStatusException {
    public BookNotIssuedToUserException() {
        super(HttpStatus.CONFLICT, "book.not_issued");
    }
}
