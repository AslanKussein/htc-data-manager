package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusHistoryRepository extends JpaRepository<OldApplicationStatusHistory, Long> {
}
