package kz.dilau.htcdatamanager.web.rest.exception;

import kz.dilau.htcdatamanager.exception.DetailedException;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestError {
    private LocalDateTime timestamp;
    private String error;
    private String message;

    public static RestError createRestException(final DetailedException de) {
        final RestError exception = new RestError();
        exception.timestamp = LocalDateTime.now();
        exception.error = de.getMessage();
        exception.message = de.getDescription();
        return exception;
    }
}
