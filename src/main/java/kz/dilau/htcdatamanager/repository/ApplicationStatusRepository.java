package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
}
