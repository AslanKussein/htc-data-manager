package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.web.dto.common.LocaledValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Data
@Slf4j
public class DetailedException extends RuntimeException {
    private final Integer statusCode;
    private final LocaledValue description;

    public DetailedException(Integer statusCode, String message, LocaledValue description) {
        super(message);
        this.statusCode = statusCode;
        this.description = description;
    }

    public DetailedException(Integer statusCode, String message, String description) {
        super(message);
        this.statusCode = statusCode;
        this.description = LocaledValue.builder()
                .kk(description)
                .ru(description)
                .en(description)
                .build();
        log.error(description);
    }

    public DetailedException(HttpStatus httpStatus, LocaledValue description) {
        super(httpStatus.getReasonPhrase());
        this.statusCode = httpStatus.value();
        this.description = description;
    }
}
