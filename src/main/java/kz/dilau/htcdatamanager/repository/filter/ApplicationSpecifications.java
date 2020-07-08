package kz.dilau.htcdatamanager.repository.filter;

import kz.dilau.htcdatamanager.domain.Application;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ApplicationSpecifications {


    public static Specification<Application> clientLoginEquals(String clientLogin) {
        return (root, cq, cb) -> cb.equal(root.get("clientLogin"), clientLogin);
    }

    public static Specification<Application> isRemovedEquals(Boolean value) {
        return (root, cq, cb) -> cb.equal(root.get("isRemoved"), value);
    }

    public static Specification<Application> operationTypeIdEquals(Long value) {
        return (root, cq, cb) -> cb.equal(root.get("operationType").get("id"), value);
    }

    public static Specification<Application> applicationStatusCodeNotEquals(String value) {
        return (root, cq, cb) -> cb.not(cb.equal(root.get("applicationStatus").get("code"), value));
    }

    public static Specification<Application> targetApplicationIdEquals(Long value) {
        return (root, cq, cb) -> cb.equal(root.get("targetApplication").get("id"), value);
    }

    public static Specification<Application> applicationStatusIdNotIn(List<Long> idList) {
        return (root, cq, cb) -> cb.not(root.get("applicationStatus").get("id").in(idList));
    }

}