package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    Optional<City> findByKazPostId(String kazPostId);
}
