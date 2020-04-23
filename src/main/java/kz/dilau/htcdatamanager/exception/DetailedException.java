package kz.dilau.htcdatamanager.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DetailedException extends RuntimeException {
    private final Integer statusCode;
    private final String description;

    public DetailedException(Integer statusCode, String message, String description) {
        super(message);
        this.statusCode = statusCode;
        this.description = description;
    }

    public DetailedException(HttpStatus httpStatus, String description) {
        super(httpStatus.getReasonPhrase());
        this.statusCode = httpStatus.value();
        this.description = description;
    }
}
