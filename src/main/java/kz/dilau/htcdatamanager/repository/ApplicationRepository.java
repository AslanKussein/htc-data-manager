package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = "SELECT * FROM htc_dm_application ORDER BY created_date DESC LIMIT 10", nativeQuery = true)
    List<Application> getRecentlyCreatedApplications();

    Optional<Application> findByIdAndIsRemovedFalse(Long id);
}
