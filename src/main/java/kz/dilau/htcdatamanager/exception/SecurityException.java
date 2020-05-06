package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.web.dto.LocaledValue;
import org.springframework.http.HttpStatus;

public class SecurityException extends DetailedException {
    public SecurityException(LocaledValue description) {
        super(HttpStatus.FORBIDDEN, description);
    }
}
