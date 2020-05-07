package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.ApplicationStatusHistory;
import kz.dilau.htcdatamanager.domain.Event;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.EventRepository;
import kz.dilau.htcdatamanager.repository.dictionary.EventTypeRepository;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EntityService entityService;
    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public Long addEvent(EventDto dto) {
        Event event = Event.builder()
                .eventDate(dto.getEventDate())
                .eventType(entityService.mapRequiredEntity(EventType.class, dto.getEventTypeId()))
                .description(dto.getDescription())
                .build();
        Application sourceApplication = entityService.mapRequiredEntity(Application.class, dto.getSourceApplicationId());
        event.setSourceApplication(sourceApplication);
        if (eventRepository.existsBySourceApplicationIdAndEventDateAndIsRemovedFalse(sourceApplication.getId(), dto.getEventDate())) {
            throw BadRequestException.createDuplicateEvent(sourceApplication.getId());
        }
        if (dto.getEventTypeId().equals(EventType.DEMO)) {
            Application targetApplication = entityService.mapRequiredEntity(Application.class, dto.getTargetApplicationId());
            event.setTargetApplication(targetApplication);
            if (eventRepository.existsByTargetApplicationIdAndEventDateAndIsRemovedFalse(targetApplication.getId(), dto.getEventDate())) {
                throw BadRequestException.createDuplicateEvent(targetApplication.getId());
            }
//            if(sourceApplication.getContractPeriod()) todo 4. Система должна произвести проверку заявки на наличие признака действий по Договору ОУ (1. договор распечатан 2. формирование договора пропущено 3. нет действий)
            if (!sourceApplication.getApplicationStatus().getId().equals(ApplicationStatus.DEMO)) {
                setStatusHistory(sourceApplication, ApplicationStatus.DEMO);
                applicationRepository.save(sourceApplication);
            }
            if (!targetApplication.getApplicationStatus().getId().equals(ApplicationStatus.DEMO)) {
                setStatusHistory(targetApplication, ApplicationStatus.DEMO);
                applicationRepository.save(targetApplication);
            }
        } else if (dto.getEventTypeId().equals(EventType.MEETING)) {
            if (!sourceApplication.getApplicationStatus().getId().equals(ApplicationStatus.MEETING)) {
                setStatusHistory(sourceApplication, ApplicationStatus.MEETING);
                applicationRepository.save(sourceApplication);
            }
        }
        return eventRepository.save(event).getId();
    }

    private void setStatusHistory(Application application, Long statusId) {
        ApplicationStatus status = entityService.mapEntity(ApplicationStatus.class, statusId);
        application.setApplicationStatus(status);
        ApplicationStatusHistory statusHistory = ApplicationStatusHistory.builder()
                .application(application)
                .applicationStatus(status)
                .build();
        application.getStatusHistoryList().add(statusHistory);
    }

    @Override
    public EventDto getEventById(Long id) {
        return new EventDto(getById(id));
    }

    private Event getById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            if (optionalEvent.get().getIsRemoved()) {
                throw EntityRemovedException.createEntityRemovedById("Event", id);
            }
            return optionalEvent.get();
        } else {
            throw NotFoundException.createEntityNotFoundById("Event", id);
        }
    }

    @Override
    public Long updateEvent(String token, Long id, EventDto dto) {
        Event event = getById(id);
        event.setEventDate(dto.getEventDate());
        event.setDescription(dto.getDescription());
        return eventRepository.save(event).getId();
    }

    @Override
    public Long commentEvent(String token, Long id, String comment) {
        Event event = getById(id);
        event.setComment(comment);
        return eventRepository.save(event).getId();
    }

    @Override
    public Long deleteEventById(String token, Long id) {
        Event event = getById(id);
        event.setIsRemoved(true);
        return eventRepository.save(event).getId();
    }
}
