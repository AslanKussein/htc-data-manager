package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.util.BundleMessageUtil;
import kz.dilau.htcdatamanager.web.dto.LocaledValue;
import org.springframework.http.HttpStatus;

public class BadRequestException extends DetailedException {
    public BadRequestException(LocaledValue description) {
        super(HttpStatus.BAD_REQUEST, description);
    }

    public static BadRequestException createRequiredIsEmpty(String name) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.required.parameter.is.empty", name));
    }

    public static BadRequestException createClientHasFounded(String phoneNumber) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.client.with.phone.number.exists", phoneNumber));
    }

    public static BadRequestException createCadastralNumberHasFounded(String cadastralNumber) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.cadastral.number.exists", cadastralNumber));
    }

    public static BadRequestException createReassignToSameAgent() {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.reassign.to.same.agent"));
    }

    public static BadRequestException createDuplicateEvent(Long appId) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.application.already.has.event.for.this.time", appId));
    }

    public static BadRequestException createEditEmail(String email) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.client.with.email.exists", email));
    }

    public static BadRequestException idMustNotBeNull() {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.id.must.not.be.null"));
    }

    public static BadRequestException createChangeStatus(String status) {
        return new BadRequestException(BundleMessageUtil.getLocaledValue("error.change.status.error", status));
    }
}
