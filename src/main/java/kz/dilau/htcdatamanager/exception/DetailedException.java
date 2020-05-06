package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.web.dto.LocaledValue;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DetailedException extends RuntimeException {
    private final Integer statusCode;
    private final LocaledValue description;

    public DetailedException(Integer statusCode, String message, LocaledValue description) {
        super(message);
        this.statusCode = statusCode;
        this.description = description;
    }

    public DetailedException(HttpStatus httpStatus, LocaledValue description) {
        super(httpStatus.getReasonPhrase());
        this.statusCode = httpStatus.value();
        this.description = description;
    }
}
