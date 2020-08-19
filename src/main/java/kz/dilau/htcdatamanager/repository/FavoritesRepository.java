package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Favorites;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FavoritesRepository extends PagingAndSortingRepository<Favorites, Long>, JpaSpecificationExecutor<Favorites> {

List<Favorites> findAllByClientLoginIsNullAndDeviceUuidEquals(String device);
 }
