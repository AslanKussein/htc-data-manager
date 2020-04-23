package kz.dilau.htcdatamanager.web.dto.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
