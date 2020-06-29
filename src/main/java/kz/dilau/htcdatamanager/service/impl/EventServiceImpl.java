package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.EventRepository;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import kz.dilau.htcdatamanager.web.dto.ProfileClientDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EntityService entityService;
    private final ApplicationRepository applicationRepository;
    private final KeycloakService keycloakService;

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
                throw EntityRemovedException.createEventRemoved(id);
            }
            return optionalEvent.get();
        } else {
            throw NotFoundException.createEventById(id);
        }
    }

    private String getViewInfo(List<ProfileClientDto> dto,Application appSource) {
        return dto
                .stream()
                .filter(l -> l.getPhoneNumber().equals(appSource.getClientLogin()))
                .map(l -> {
                    StringBuilder sb = new StringBuilder().append(l.getFullname()).append(", ");
                    if (nonNull(appSource.getApplicationSellData())) {
                        RealProperty rp = appSource.getApplicationSellData().getRealProperty();
                        if (nonNull(rp)) {
                            sb.append(DictionaryMappingTool.mapAddressToMultiLang(rp.getBuilding(), rp.getApartmentNumber()).getNameRu());
                        }
                    }
                    return sb.toString();
                })
                .findFirst().orElse("");
    }

    @Override
    public List<JasperActViewDto> getViewbyTargetApp(Long appId) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        List<Event> elst = eventRepository
                .findByTargetApplicationIdAndEventTypeId(appId, EventType.DEMO);

        List<JasperActViewDto> lst = new ArrayList();

        if (elst.size() > 0) {
            List<String> logins = elst.stream()
                    .map(t -> t.getSourceApplication().getClientLogin())
                    .collect(Collectors.toList());

            System.out.println(logins);
            List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(logins);
            //ListResponse<UserInfoDto> dto = keycloakService.readUserInfos(logins);
            System.out.println(profileClientDtoList);
            int idx = 0;
            for ( Event t : elst) {
                idx++;
                lst.add(
                        new JasperActViewDto(String.valueOf(idx),
                                sdfDate.format(t.getEventDate()),
                                getViewInfo(profileClientDtoList, t.getSourceApplication()))
                );
            }


            /*lst = elst.stream()
                    .map(t -> {
                                JasperActViewDto d = new JasperActViewDto(t.getId().toString(),
                                        sdfDate.format(t.getEventDate()),
                                        getViewInfo(profileClientDtoList, t.getSourceApplication()));
                                return d;
                            }
                    )
                    .collect(Collectors.toList());*/
        }

        return lst;
    }
    @Override
    public List<JasperActViewDto> getViewBySourceApp(Long appId) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        List<Event> elst = eventRepository
                .findBySourceApplicationIdAndEventTypeId(appId, EventType.DEMO);

        List<JasperActViewDto> lst = new ArrayList();

        if (elst.size() > 0) {
            List<String> logins = elst.stream()
                    .map(t -> t.getTargetApplication().getClientLogin())
                    .collect(Collectors.toList());

            System.out.println(logins);
            List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(logins);
            System.out.println(profileClientDtoList);


            int idx = 0;
            for ( Event t : elst) {
                idx++;
                lst.add(
                        new JasperActViewDto(String.valueOf(idx),
                                sdfDate.format(t.getEventDate()),profileClientDtoList
                                .stream()
                                .filter(l -> l.getPhoneNumber().equals(t.getTargetApplication().getClientLogin()))
                                .map( x -> x.getFullname())
                                .findFirst()
                                .orElse("")
                        )
                );
            }
        }

        return lst;
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
