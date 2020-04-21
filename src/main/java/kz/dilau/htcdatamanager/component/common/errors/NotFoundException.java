package kz.dilau.htcdatamanager.component.common.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
