package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Optional<Settings> findByKey(String pKey);
}
