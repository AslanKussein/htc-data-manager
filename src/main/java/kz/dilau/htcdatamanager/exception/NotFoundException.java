package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DetailedException {
    public NotFoundException(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static NotFoundException createEntityNotFoundById(String name, Long id) {
        return new NotFoundException(String.format("Entity %s with ID = %s not found", name, id));
    }

    public static NotFoundException createOwnerNotFoundById(Long id) {
        return new NotFoundException(String.format("Owner with ID = %s not found", id));
    }

    public static NotFoundException createApplicationNotFoundById(Long id) {
        return new NotFoundException(String.format("Application with ID = %s not found", id));
    }
}
