package name.saifmahmud.demo.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Map<String, Object>> errors = new HashMap<>();

        ex.getFieldErrors().forEach(error -> {
            Map<String, Object> err = new HashMap<>();

            err.put("code", error.getCode());
            err.put("message", error.getDefaultMessage());

            errors.put(error.getField(), err);
        });

        return handleExceptionInternal(new RuntimeException("exceptions.validation_error", ex), errors, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusExceptions(ResponseStatusException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, ex.getResponseHeaders(), ex.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object errors, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        String message;

        if (ex instanceof ResponseStatusException) {
            message = ((ResponseStatusException) ex).getReason();
        } else {
            message = ex.getMessage();
        }

        try {
            if (message == null) {
                message = ex.getLocalizedMessage();
            } else {
                message = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
            }
        } catch (NoSuchMessageException e) {
            message = ex.getLocalizedMessage();
        }

        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", message);
        body.put("errors", errors);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
