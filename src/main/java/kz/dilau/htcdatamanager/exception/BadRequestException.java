package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DetailedException {
    public BadRequestException(String description) {
        super(HttpStatus.BAD_REQUEST, description);
    }

    public static BadRequestException createRequiredIsEmpty() {
        return new BadRequestException("Required parameter is empty");
    }

    public static BadRequestException createClientHasFounded(String phoneNumber) {
        return new BadRequestException(String.format("Client with phoneNumber $s founded", phoneNumber));
    }
}
