package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
