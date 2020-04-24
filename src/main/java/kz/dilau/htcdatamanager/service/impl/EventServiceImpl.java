package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.Event;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.repository.EventRepository;
import kz.dilau.htcdatamanager.repository.dictionary.EventTypeRepository;
import kz.dilau.htcdatamanager.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final ApplicationRepository applicationRepository;
    private final EventTypeRepository eventTypeRepository;

    @Override
    public Long addEvent(String token, EventDto event) {
        Event event1 = new Event();
        event1.setEventDate(event.getEventDate());

        Application one = applicationRepository.getOne(event.getSourceApplicationId());
        event1.setSourceApplication(one);
        if (event.getTargetApplicationId() != null) {
            Application one1 = applicationRepository.getOne(event.getTargetApplicationId());
            event1.setTargetApplication(one1);
        }
        EventType one1 = eventTypeRepository.getOne(event.getEventTypeId());
        event1.setEventType(one1);
        event1.setClientId(event.getClientId());
        event1.setDescription(event.getDescription());
        event1.setComment(event.getComment());
        Long id = eventRepository.save(event1).getId();
        return id;
    }

    @Override
    public EventDto getEventById(String token, Long id) {
        return eventRepository
                .findById(id)
                .map(EventDto::new)
                .orElseThrow(() -> new NotFoundException("Event with id %s not found" + id));
    }

    @Override
    public void updateEvent(String token, Long id, EventDto event) {
        Event entity = eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id %s not found" + id));
        entity.setEventDate(event.getEventDate());

        Application one = applicationRepository.getOne(event.getSourceApplicationId());
        entity.setSourceApplication(one);
        if (event.getTargetApplicationId() != null) {
            Application one1 = applicationRepository.getOne(event.getTargetApplicationId());
            entity.setTargetApplication(one1);
        }
        EventType one1 = eventTypeRepository.getOne(event.getEventTypeId());
        entity.setEventType(one1);


        entity.setClientId(event.getClientId());
        entity.setDescription(event.getDescription());
        entity.setComment(event.getComment());
        entity.setClientId(event.getClientId());
        eventRepository.save(entity);
    }

    @Override
    public void deleteEventById(String token, Long id) {
        boolean exists = eventRepository.existsById(id);
        if (!exists) throw new NotFoundException("Event with id %s not found" + id);
        eventRepository.deleteById(id);
    }
}
