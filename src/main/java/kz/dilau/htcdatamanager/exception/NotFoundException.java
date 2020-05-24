package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.util.BundleMessageUtil;
import kz.dilau.htcdatamanager.web.dto.common.LocaledValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class NotFoundException extends DetailedException {
    public NotFoundException(LocaledValue description) {
        super(HttpStatus.NOT_FOUND, description);
        log.error(description.toString());
    }

    public static NotFoundException createEntityNotFoundById(String name, Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.entity.not.found", name, id));
    }

    public static NotFoundException createEntityNotFoundByKazPostId(String name, String id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.entity.not.found.kazPostId", name, id));
    }

    public static NotFoundException createMortgageById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.mortgage.not.found", id));
    }

    public static NotFoundException createResidentialComplexById(Long id) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.residential.complex.not.found", id));
    }

    public static NotFoundException createResidentialComplexByPostcode(String postcode) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.residential.complex.postcode.not.found", postcode));
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

    public static NotFoundException createApartmentByNumberAndPostcode(String apartmentNumber, String postcode) {
        return new NotFoundException(BundleMessageUtil.getLocaledValue("error.apartment.not.found", apartmentNumber, postcode));
    }
}
