package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building getByPostcodeAndIsRemovedFalse(String postcode);
}
