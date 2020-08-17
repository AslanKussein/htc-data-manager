package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationContractInfoDto;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import kz.dilau.htcdatamanager.web.dto.EventViewDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;

import java.util.List;

public interface EventService {
    Long saveEvent(EventDto event, boolean fromClient);

    EventViewDto getEventById(Long id);

    Long updateEvent(String token, Long id, EventDto event);

    Long commentEvent(String token, Long id, String comment);

    Long deleteEventById(String token, Long id);

    List<JasperActViewDto> getViewbyTargetApp(Long appId);

    List<JasperActViewDto> getViewBySourceApp(Long appId);

    ApplicationContractInfoDto getContractsInfo(Long applicationId);
}
