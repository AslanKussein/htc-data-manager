package kz.dilau.htcdatamanager.repository.filter;

import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.jpa.domain.Specification;

public class FavoriteSpecifications {


    public static Specification<Favorites> clientLoginEquals(String clientLogin) {
        return (root, cq, cb) -> cb.equal(root.get("clientLogin"), clientLogin);
    }


    public static Specification<Favorites> realPropertyIdEquals(Long realPropertyId) {
        return (root, cq, cb) -> cb.equal(root.get("realProperty").get("id"), realPropertyId);
    }

    public static Specification<Favorites> isRemovedEquals(Boolean value) {
        return (root, cq, cb) -> cb.equal(root.get("realProperty").get("sellDataList").get("sellDataList").get("application").get("isRemoved"), value);
    }
}
