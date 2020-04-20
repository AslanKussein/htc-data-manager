package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<GeneralCharacteristics, Long> {
}
