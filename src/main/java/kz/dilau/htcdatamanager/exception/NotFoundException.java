package kz.dilau.htcdatamanager.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DetailedException {
    public NotFoundException(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static NotFoundException createUserNotFoundById(Long id) {
        return new NotFoundException(String.format("User with ID = %s not found", id));
    }

    public static NotFoundException createUserNotFoundByLogin(String login) {
        return new NotFoundException(String.format("User with Login = %s not found", login));
    }

    public static NotFoundException createGroupNotFoundById(Long id) {
        return new NotFoundException(String.format("Group with ID = %s not found", id));
    }

    public static NotFoundException createRoleNotFoundById(Long id) {
        return new NotFoundException(String.format("Role with ID = %s not found", id));
    }
}
