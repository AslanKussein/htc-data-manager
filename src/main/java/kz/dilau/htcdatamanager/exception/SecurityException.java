package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class SecurityException extends DetailedException {
    public SecurityException(String description) {
        super(HttpStatus.FORBIDDEN, description);
    }
}
