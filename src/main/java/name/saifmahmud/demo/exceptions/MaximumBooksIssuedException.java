package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MaximumBooksIssuedException extends ResponseStatusException {
    public MaximumBooksIssuedException() {
        super(HttpStatus.FORBIDDEN, "book.maximum_issued");
    }
}
