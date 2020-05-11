package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ParkingTypeRepository extends JpaRepository<ParkingType, Long> {
    Set<ParkingType> findByIdIn(List<Long> ids);

}
