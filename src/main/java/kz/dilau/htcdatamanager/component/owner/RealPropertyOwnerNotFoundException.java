package kz.dilau.htcdatamanager.component.owner;

public class RealPropertyOwnerNotFoundException extends RuntimeException {
    public RealPropertyOwnerNotFoundException(Long id) {
        super("RealPropertyOwner with id not found: " + id);
    }
}
