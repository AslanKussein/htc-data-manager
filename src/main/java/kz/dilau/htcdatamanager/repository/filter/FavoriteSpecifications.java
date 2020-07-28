package kz.dilau.htcdatamanager.repository.filter;

import kz.dilau.htcdatamanager.domain.ApplicationSellData;
import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class FavoriteSpecifications {

    public static Specification<Favorites> filter(String clientLogin,
                                                  Boolean strictDevice,
                                                  List<String> deviceUuids,
                                                  Long realPropertyId) {

        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false);

        if (nonNull(clientLogin)) {
            if (nonNull(strictDevice) && strictDevice) {
                specification = specification.and(FavoriteSpecifications.deviceUuidIn(deviceUuids));
            } else {
                Specification<Favorites> specInner = FavoriteSpecifications.clientLoginEquals(clientLogin);
                specification = specification.and(specInner.or(FavoriteSpecifications.deviceUuidIn(deviceUuids)));
            }
        } else {
            specification = specification.and(FavoriteSpecifications.deviceUuidIn(deviceUuids));
        }

        if (nonNull(realPropertyId)) {
            specification = specification.and(FavoriteSpecifications.realPropertyIdEquals(realPropertyId));
        }
        return specification;
    }

    public static Specification<Favorites> clientLoginEquals(String clientLogin) {
        return (root, cq, cb) -> cb.equal(root.get("clientLogin"), clientLogin);
    }

    public static Specification<Favorites> realPropertyIdEquals(Long realPropertyId) {
        return (root, cq, cb) -> cb.equal(root.get("realProperty").get("id"), realPropertyId);
    }

    public static Specification<Favorites> isRemovedEquals(Boolean value) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Subquery<ApplicationSellData> dataCQ = cq.subquery(ApplicationSellData.class);
            Root<ApplicationSellData> dataRoot = dataCQ.from(ApplicationSellData.class);
            dataCQ.select(dataRoot);
            List<Predicate> dataPredicates = new ArrayList<>();
            dataPredicates.add(cb.equal(root.get("realProperty").get("id"), dataRoot.get("realProperty").get("id")));
            dataPredicates.add(cb.equal(dataRoot.get("application").get("isRemoved"), value));

            predicates.add(cb.exists(dataCQ.where(dataPredicates.toArray(new Predicate[]{}))));
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }

    public static Specification<Favorites> deviceUuidEquals(String uuid) {
        return (root, cq, cb) -> cb.equal(root.get("deviceUuid"), uuid);
    }

    public static Specification<Favorites> deviceUuidIn(List<String> uuids) {
        return (root, cq, cb) -> root.get("deviceUuid").in( uuids);
    }
}
