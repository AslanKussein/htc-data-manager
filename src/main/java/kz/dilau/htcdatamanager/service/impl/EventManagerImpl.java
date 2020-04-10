package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Event;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.repository.EventRepository;
import kz.dilau.htcdatamanager.service.EventManager;
import kz.dilau.htcdatamanager.web.rest.vm.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventManagerImpl implements EventManager {
    private final EventRepository eventRepository;
    private final ApplicationStatusRepository applicationStatusRepository;

    @Override
    public Long addEvent(String token, EventDto event) {
        Event event1 = new Event();
        event1.setEventDate(event.getEventDate());
        ApplicationStatus applicationStatus = applicationStatusRepository.getOne(event.getApplicationStatusId());
        event1.setApplicationStatus(applicationStatus);
        event1.setApplicationsIds(event.getApplicationsIds());
        event1.setClientId(event.getClientId());
        event1.setRealPropertiesIds(event.getRealPropertiesIds());
        event1.setDescription(event.getDescription());
        event1.setComment(event.getComment());
        Long id = eventRepository.save(event1).getId();
        return id;
    }

    @Override
    public EventDto getEventById(String token, Long id) {
        Event event = eventRepository.getOne(id);
        return new EventDto(event);
    }

    @Override
    public void updateEvent(String token, Long id, EventDto event) {
        Event entity = eventRepository.getOne(id);
        entity.setEventDate(event.getEventDate());
        ApplicationStatus applicationStatus = applicationStatusRepository.getOne(event.getApplicationStatusId());
        entity.setApplicationStatus(applicationStatus);
        entity.setApplicationsIds(event.getApplicationsIds());
        entity.setClientId(event.getClientId());
        entity.setRealPropertiesIds(event.getRealPropertiesIds());
        entity.setDescription(event.getDescription());
        entity.setComment(event.getComment());
        entity.setClientId(event.getClientId());
        eventRepository.save(entity);
    }

    @Override
    public void deleteEventById(String token, Long id) {
        eventRepository.deleteById(id);
    }
}
