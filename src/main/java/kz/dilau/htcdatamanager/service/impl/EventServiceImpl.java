package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ContractStatus;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.EventRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationContractInfoDto;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import kz.dilau.htcdatamanager.web.dto.ProfileClientDto;
import kz.dilau.htcdatamanager.web.dto.common.DatePeriod;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EntityService entityService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final KeycloakService keycloakService;
    private final DataProperties dataProperties;

    private void setStatusHistoryAndSaveApplication(Application application, Long statusId) {
        if (!application.getApplicationStatus().getId().equals(statusId)) {
            ApplicationStatus status = entityService.mapEntity(ApplicationStatus.class, statusId);
            application.setApplicationStatus(status);
            ApplicationStatusHistory statusHistory = ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(status)
                    .build();
            application.getStatusHistoryList().add(statusHistory);
            applicationRepository.save(application);
        }
    }

    @Transactional
    public Long saveEvent(EventDto dto, boolean fromClient) {
        if (isNull(dto.getEventDate())) {
            throw BadRequestException.createRequiredIsEmpty("EventDate");
        }
        ZonedDateTime eventDate = ZonedDateTime.from(dto.getEventDate()).toInstant().atZone(ZoneId.systemDefault());
        Event event = Event.builder()
                .eventDate(eventDate)
                .eventType(entityService.mapRequiredEntity(EventType.class, dto.getEventTypeId()))
                .description(dto.getDescription())
                .build();
        Application sourceApplication = applicationService.getApplicationById(dto.getSourceApplicationId());
        event.setSourceApplication(sourceApplication);
        if (eventRepository.existsBySourceApplicationIdAndEventDateAndIsRemovedFalse(sourceApplication.getId(), eventDate)) {
            throw BadRequestException.createTemplateException("error.event.date.duplicate");
        }
        if (dto.getEventTypeId().equals(EventType.DEMO)) {
            DatePeriod period = DatePeriod.builder().from(dto.getEventDate().atStartOfDay(ZoneOffset.UTC).with(LocalTime.MIN))
                    .to(dto.getEventDate().atStartOfDay(ZoneOffset.UTC).with(LocalTime.MAX)).build();
            if (eventRepository.countBySourceApplicationIdAndEventDateBetween(dto.getSourceApplicationId(), period.getFrom(), period.getTo()) >= dataProperties.getMaxEventDemoPerDay()) {
                throw BadRequestException.createTemplateExceptionWithParam("error.max.event.demo.per.day", dto.getSourceApplicationId().toString());
            }
            if (isNull(dto.getTargetApplicationId())) {
                throw BadRequestException.createRequiredIsEmpty("targetApplicationId");
            }
            Application targetApplication = applicationService.getApplicationById(dto.getTargetApplicationId());
            event.setTargetApplication(targetApplication);
            if (sourceApplication.getOperationType().getId().equals(targetApplication.getOperationType().getId())) {
                throw BadRequestException.createTemplateException("error.operation.type.in.target.application");
            }
            if (eventRepository.existsByTargetApplicationIdAndEventDateAndIsRemovedFalse(targetApplication.getId(), eventDate)) {
                throw BadRequestException.createTemplateException("error.event.date.duplicate");
            }
            if (isNull(sourceApplication.getContract())) {
                if (fromClient) {
                    sourceApplication.setContract(ApplicationContract.builder()
                            .application(sourceApplication)
                            .contractStatus(entityService.mapEntity(ContractStatus.class, ContractStatus.MISSING))
                            .build());
                    ApplicationStatus applicationStatus = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.CONTRACT);
                    sourceApplication.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                            .application(sourceApplication)
                            .applicationStatus(applicationStatus)
                            .build());
                    sourceApplication.setApplicationStatus(applicationStatus);
                } else {
                    throw BadRequestException.createTemplateException("error.empty.contract");
                }
            }
            setStatusHistoryAndSaveApplication(sourceApplication, ApplicationStatus.DEMO);
            setStatusHistoryAndSaveApplication(targetApplication, ApplicationStatus.DEMO);
        } else if (dto.getEventTypeId().equals(EventType.MEETING)) {
            setStatusHistoryAndSaveApplication(sourceApplication, ApplicationStatus.MEETING);
        }
        return eventRepository.save(event).getId();
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

    private String getViewInfo(List<ProfileClientDto> dto, Application appSource) {
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
            for (Event t : elst) {
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
            for (Event t : elst) {
                idx++;
                lst.add(
                        new JasperActViewDto(String.valueOf(idx),
                                sdfDate.format(t.getEventDate()), profileClientDtoList
                                .stream()
                                .filter(l -> l.getPhoneNumber().equals(t.getTargetApplication().getClientLogin()))
                                .map(x -> x.getFullname())
                                .findFirst()
                                .orElse("")
                        )
                );
            }
        }

        return lst;
    }

    @Override
    public ApplicationContractInfoDto getContractsInfo(Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        return ApplicationContractInfoDto.builder()
                .applicationId(application.getId())
                .contractStatus(nonNull(application.getContract()) ? DictionaryMappingTool.mapMultilangSystemDictionary(application.getContract().getContractStatus()) : null)
                .hasDepositContract(nonNull(application.getDeposit()) || nonNull(application.getSellDeposit()))
                .build();
    }

    @Override
    public Long updateEvent(String token, Long id, EventDto dto) {
        Event event = getById(id);
        event.setEventDate(nonNull(dto.getEventDate()) ? ZonedDateTime.from(dto.getEventDate()).toInstant().atZone(ZoneId.systemDefault()) : null);
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
