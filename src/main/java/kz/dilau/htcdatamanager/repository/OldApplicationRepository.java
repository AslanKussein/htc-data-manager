package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OldApplicationRepository extends JpaRepository<OldApplication, Long> {
    @Query(value = "SELECT * FROM htc_dm_application ORDER BY created_date DESC LIMIT 10", nativeQuery = true)
    List<OldApplication> getRecentlyCreatedApplications();

    Optional<OldApplication> findByIdAndIsRemovedFalse(Long id);
}
