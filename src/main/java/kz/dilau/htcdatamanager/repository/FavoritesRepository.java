package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends PagingAndSortingRepository<Favorites, Long> {

    @Query(value = "select distinct f from Favorites f " +
            "join ApplicationSellData s on s.realProperty.id = f.realProperty.id  " +
            "join Application a on a.id = s.application.id " +
            "where a.isRemoved = false and f.clientLogin = :clientLogin")
    Page<Favorites> findAllByClientLogin(@Param("clientLogin") String clientLogin, Pageable page);

    @Query(value = "select distinct f.realProperty.id from Favorites f " +
            "join ApplicationSellData s on s.realProperty.id = f.realProperty.id  " +
            "join Application a on a.id = s.application.id " +
            "where a.isRemoved = false and f.clientLogin = :clientLogin")
    List<Long> findAllByClientLogin(@Param("clientLogin") String clientLogin);

    @Query(value = "select distinct f from Favorites f " +
            "join ApplicationSellData s on s.realProperty.id = f.realProperty.id  " +
            "join Application a on a.id = s.application.id " +
            "where a.isRemoved = false and f.realProperty.id = :realPropertyId and f.clientLogin = :clientLogin")
    Optional<Favorites> findByRealProperty_IdAndClientLogin(@Param("realPropertyId") Long realPropertyId, @Param("clientLogin") String clientLogin);
}
