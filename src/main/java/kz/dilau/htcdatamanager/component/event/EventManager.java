package kz.dilau.htcdatamanager.component.event;

public interface EventManager {
    Long addEvent(String token, EventDto event);

    EventDto getEventById(String token, Long id);

    void updateEvent(String token, Long id, EventDto event);

    void deleteEventById(String token, Long id);
}
