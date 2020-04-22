package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;

import java.util.List;
import java.util.Set;

public interface ParkingTypeRepository extends DictionaryRepository<ParkingType> {
    Set<ParkingType> findByIdIn(List<Long> ids);

}
