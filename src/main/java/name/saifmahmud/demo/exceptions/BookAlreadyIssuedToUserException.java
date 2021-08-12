package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyIssuedToUserException extends RuntimeException {
    public BookAlreadyIssuedToUserException() {
        super("book.already_issued");
    }
}
