package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DetailedException {
    public BadRequestException(String description) {
        super(HttpStatus.BAD_REQUEST, description);
    }

    public static BadRequestException createRequiredIsEmpty(String name) {
        return new BadRequestException(String.format("Required parameter %s is empty", name));
    }

    public static BadRequestException createClientHasFounded(String phoneNumber) {
        return new BadRequestException(String.format("Client with phoneNumber %s founded in DB", phoneNumber));
    }

    public static BadRequestException createCadastralNumberHasFounded(String cadastralNumber) {
        return new BadRequestException(String.format("CadastralNumber %s founded in DB", cadastralNumber));
    }

    public static BadRequestException createReassignToSameAgent() {
        return new BadRequestException("You can't reassign a request to the same agent");
    }
}
