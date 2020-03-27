package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = "SELECT * FROM htc_dm_application LIMIT 10", nativeQuery = true)
    List<Application> getRecentlyCreatedApps();
}
