package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookNotIssuedToUserException extends RuntimeException {
    public BookNotIssuedToUserException() {
        super("book.not_issued");
    }
}
