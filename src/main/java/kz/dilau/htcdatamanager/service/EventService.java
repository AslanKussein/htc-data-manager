package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.EventDto;

public interface EventService {
    Long addEvent(EventDto event);

    EventDto getEventById(Long id);

    Long updateEvent(String token, Long id, EventDto event);

    Long commentEvent(String token, Long id, String comment);

    Long deleteEventById(String token, Long id);
}
