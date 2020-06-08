package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends PagingAndSortingRepository<Favorites, Long>, JpaSpecificationExecutor<Favorites> {

    List<Favorites> findAllByClientLogin(String clientLogin, Pageable page);

    List<Favorites> findAllByClientLogin(String clientLogin );

    Optional<Favorites> findByRealProperty_IdAndClientLogin(Long realPropertyId, String clientLogin);
}
