package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ZeroBooksLeftForIssueException extends ResponseStatusException {
    public ZeroBooksLeftForIssueException() {
        super(HttpStatus.FORBIDDEN, "book.all_issued");
    }
}
