package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldGeneralCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralCharacteristicsRepository extends JpaRepository<OldGeneralCharacteristics, Long> {
}
