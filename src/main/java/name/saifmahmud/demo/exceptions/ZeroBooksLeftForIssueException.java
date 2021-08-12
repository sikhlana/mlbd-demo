package name.saifmahmud.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ZeroBooksLeftForIssueException extends RuntimeException {
    public ZeroBooksLeftForIssueException() {
        super("book.all_issued");
    }
}
