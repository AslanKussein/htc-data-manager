package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Long> {
}
