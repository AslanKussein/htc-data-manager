package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    List<Favorites> findAllByClientLogin(String clientLogin);

    Favorites findByRealPropertyIdAndClientLogin(Long realPropertyId, String clientLogin);
}
