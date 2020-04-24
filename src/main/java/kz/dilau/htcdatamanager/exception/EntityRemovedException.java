package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class EntityRemovedException extends DetailedException {
    public EntityRemovedException(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static EntityRemovedException createEntityRemovedById(String name, Long id) {
        return new EntityRemovedException(String.format("Entity %s with ID = %s removed", name, id));
    }

    public static EntityRemovedException createClientRemovedById(Long id) {
        return new EntityRemovedException(String.format("Client with ID = %s removed", id));
    }

    public static EntityRemovedException createApplicationRemovedById(Long id) {
        return new EntityRemovedException(String.format("Application with ID = %s removed", id));
    }
}