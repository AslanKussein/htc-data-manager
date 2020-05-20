package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.KazPostData;
import kz.dilau.htcdatamanager.domain.enums.KazPostDataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KazPostDataRepository extends JpaRepository<KazPostData, Long> {

    Boolean existsByIdAndStatus(String id, KazPostDataStatus status);

    List<KazPostData> findAllByStatus(KazPostDataStatus status);
}