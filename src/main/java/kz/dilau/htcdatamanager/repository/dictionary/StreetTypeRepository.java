package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.StreetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetTypeRepository extends JpaRepository<StreetType, String> {
}