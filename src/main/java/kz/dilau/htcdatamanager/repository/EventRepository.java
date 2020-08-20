package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsBySourceApplicationIdAndEventDateAndIsRemovedFalse(Long sourceApplicationId, ZonedDateTime date);

    boolean existsByTargetApplicationIdAndEventDateAndIsRemovedFalse(Long targetApplicationId, ZonedDateTime date);

    int countBySourceApplicationIdAndEventDateBetween(Long sourceApplicationId, ZonedDateTime from, ZonedDateTime to);

    List<Event> findByTargetApplicationIdAndEventTypeId(Long app, Long et);

    List<Event> findBySourceApplicationIdAndEventTypeId(Long app, Long et);

    List<Event> findBySourceApplicationId(Long app);
}
