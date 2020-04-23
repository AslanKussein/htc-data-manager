package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.EventDto;

public interface EventManager {
    Long addEvent(String token, EventDto event);

    EventDto getEventById(String token, Long id);

    void updateEvent(String token, Long id, EventDto event);

    void deleteEventById(String token, Long id);
}
