package kz.dilau.htcdatamanager.web.rest.exception;

import kz.dilau.htcdatamanager.exception.DetailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * REST exception handler.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int DEFAULT_STATUS_CODE = 500;

    @ExceptionHandler(DetailedException.class)
    protected ResponseEntity<Object> handleDetailedException(final DetailedException de) {
        return ResponseEntity.status(de.getStatusCode() == null ? DEFAULT_STATUS_CODE : de.getStatusCode()).body(RestError.createRestException(de));
    }
}
