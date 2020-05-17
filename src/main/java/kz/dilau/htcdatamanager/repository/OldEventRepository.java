package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OldEventRepository extends JpaRepository<OldEvent, Long> {
    boolean existsBySourceApplicationIdAndEventDateAndIsRemovedFalse(Long sourceApplicationId, Date date);

    boolean existsByTargetApplicationIdAndEventDateAndIsRemovedFalse(Long targetApplicationId, Date date);
}
