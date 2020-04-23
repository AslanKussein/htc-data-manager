package kz.dilau.htcdatamanager.exception;

public class RealPropertyOwnerNotFoundException extends RuntimeException {
    public RealPropertyOwnerNotFoundException(Long id) {
        super("RealPropertyOwner with id not found: " + id);
    }
}
