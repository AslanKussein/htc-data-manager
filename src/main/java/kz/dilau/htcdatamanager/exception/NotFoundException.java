package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.util.BundleMessageUtil;
import kz.dilau.htcdatamanager.web.dto.LocaledValue;
import org.springframework.http.HttpStatus;

public class NotFoundException extends DetailedException {
    public NotFoundException(LocaledValue description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static NotFoundException createEntityNotFoundById(String name, Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.entity.not.found", name, id));
    }

    public static NotFoundException createMortgageById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.mortgage.not.found", id));
    }

    public static NotFoundException createResidentialComplexById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.residential.complex.not.found", id));
    }

    public static NotFoundException createEventById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.event.not.found", id));
    }

    public static NotFoundException createClientById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.client.not.found", id));
    }

    public static NotFoundException createApplicationById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.application.not.found", id));
    }

    public static NotFoundException createRealPropertyNotFoundById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.real.property.not.found", id));
    }

    public static NotFoundException createNotesById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.notes.not.found", id));
    }
}
