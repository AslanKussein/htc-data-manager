package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsBySourceApplicationIdAndEventDate(Long sourceApplicationId, Date date);

    boolean existsByTargetApplicationIdAndEventDate(Long targetApplicationId, Date date);
}
