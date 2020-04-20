package kz.dilau.htcdatamanager.web.rest.errors;

public class RealPropertyOwnerNotFoundException extends RuntimeException {
    public RealPropertyOwnerNotFoundException(Long id) {
        super("RealPropertyOwner with id not found: " + id);
    }
}
