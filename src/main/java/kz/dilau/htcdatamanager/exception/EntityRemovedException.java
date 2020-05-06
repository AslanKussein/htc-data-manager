package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.util.BundleMessageUtil;
import kz.dilau.htcdatamanager.web.dto.LocaledValue;
import org.springframework.http.HttpStatus;

public class EntityRemovedException extends DetailedException {
    public EntityRemovedException(LocaledValue description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static EntityRemovedException createEntityRemovedById(String name, Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("Entity %s with ID = %s removed", name, id));
    }

    public static EntityRemovedException createResidentialComplexRemoved(Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("error.residential.complex.removed", id));
    }

    public static EntityRemovedException createMortgageRemoved(Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("error.mortgage.removed", id));
    }

    public static EntityRemovedException createEventRemoved(Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("error.event.removed", id));
    }

    public static EntityRemovedException createClientRemoved(Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("error.client.removed", id));
    }

    public static EntityRemovedException createApplicationRemoved(Long id) {
        return new EntityRemovedException(BundleMessageUtil.getLocaledValue("error.application.removed", id));
    }
}