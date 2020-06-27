package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ChangeStatusDto;
import kz.dilau.htcdatamanager.web.dto.CompleteDealDto;
import kz.dilau.htcdatamanager.web.dto.ConfirmCompleteDto;
import kz.dilau.htcdatamanager.web.dto.ForceCompleteDto;

public interface KanbanService {
    Long changeStatus(ChangeStatusDto dto);

    Long completeDeal(CompleteDealDto dto);

    Long forceCompleteDeal(ForceCompleteDto dto);

    Long confirmComplete(ConfirmCompleteDto dto);
}
