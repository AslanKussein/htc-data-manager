package kz.dilau.htcdatamanager.repository.filter;

import kz.dilau.htcdatamanager.domain.ApplicationSellData;
import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

public class FavoriteSpecifications {


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
}
