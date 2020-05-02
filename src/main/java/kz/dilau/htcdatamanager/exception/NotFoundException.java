package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DetailedException {
    public NotFoundException(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static NotFoundException createEntityNotFoundById(String name, Long id) {
        return new NotFoundException(String.format("Entity %s with ID = %s not found", name, id));
    }

    public static NotFoundException createClientNotFoundById(Long id) {
        return new NotFoundException(String.format("Client with ID = %s not found", id));
    }

    public static NotFoundException createApplicationNotFoundById(Long id) {
        return new NotFoundException(String.format("Application with ID = %s not found", id));
    }

    public static NotFoundException findByNumber(String number) {
        return new NotFoundException(String.format("Client with number = %s not found", number));
    }
}
