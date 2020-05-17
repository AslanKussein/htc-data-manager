package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.old.OldEvent;
import kz.dilau.htcdatamanager.domain.old.OldApplication;
import kz.dilau.htcdatamanager.domain.old.OldApplicationStatusHistory;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.OldApplicationRepository;
import kz.dilau.htcdatamanager.repository.OldEventRepository;
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
    private final OldEventRepository eventRepository;
    private final EntityService entityService;
    private final OldApplicationRepository applicationRepository;

    @Override
    @Transactional
    public Long addEvent(EventDto dto) {
        OldEvent event = OldEvent.builder()
                .eventDate(dto.getEventDate())
                .eventType(entityService.mapRequiredEntity(EventType.class, dto.getEventTypeId()))
                .description(dto.getDescription())
                .build();
        OldApplication sourceApplication = entityService.mapRequiredEntity(OldApplication.class, dto.getSourceApplicationId());
        event.setSourceApplication(sourceApplication);
        if (eventRepository.existsBySourceApplicationIdAndEventDateAndIsRemovedFalse(sourceApplication.getId(), dto.getEventDate())) {
            throw BadRequestException.createDuplicateEvent(sourceApplication.getId());
        }
        if (dto.getEventTypeId().equals(EventType.DEMO)) {
            OldApplication targetApplication = entityService.mapRequiredEntity(OldApplication.class, dto.getTargetApplicationId());
            event.setTargetApplication(targetApplication);
            if (sourceApplication.getOperationType().getId().equals(targetApplication.getOperationType().getId())) {
                throw BadRequestException.createRequiredIsEmpty("");
            }
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

    private void setStatusHistory(OldApplication application, Long statusId) {
        ApplicationStatus status = entityService.mapEntity(ApplicationStatus.class, statusId);
        application.setApplicationStatus(status);
        OldApplicationStatusHistory statusHistory = OldApplicationStatusHistory.builder()
                .application(application)
                .applicationStatus(status)
                .build();
        application.getStatusHistoryList().add(statusHistory);
    }

    @Override
    public EventDto getEventById(Long id) {
        return new EventDto(getById(id));
    }

    private OldEvent getById(Long id) {
        Optional<OldEvent> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            if (optionalEvent.get().getIsRemoved()) {
                throw EntityRemovedException.createEventRemoved(id);
            }
            return optionalEvent.get();
        } else {
            throw NotFoundException.createEventById(id);
        }
    }

    @Override
    public Long updateEvent(String token, Long id, EventDto dto) {
        OldEvent event = getById(id);
        event.setEventDate(dto.getEventDate());
        event.setDescription(dto.getDescription());
        return eventRepository.save(event).getId();
    }

    @Override
    public Long commentEvent(String token, Long id, String comment) {
        OldEvent event = getById(id);
        event.setComment(comment);
        return eventRepository.save(event).getId();
    }

    @Override
    public Long deleteEventById(String token, Long id) {
        OldEvent event = getById(id);
        event.setIsRemoved(true);
        return eventRepository.save(event).getId();
    }
}
