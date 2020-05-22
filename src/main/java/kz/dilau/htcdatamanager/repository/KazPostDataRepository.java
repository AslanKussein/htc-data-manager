package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.KazPostData;
import kz.dilau.htcdatamanager.domain.enums.KazPostDataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KazPostDataRepository extends JpaRepository<KazPostData, Long> {
    Optional<KazPostData> findByIdAndStatus(String id, KazPostDataStatus status);
}