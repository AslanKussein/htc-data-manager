package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ChangeStatusDto;
import kz.dilau.htcdatamanager.web.dto.CompleteDealDto;
import kz.dilau.htcdatamanager.web.dto.ConfirmDealDto;
import kz.dilau.htcdatamanager.web.dto.ForceCloseDealDto;

public interface KanbanService {
    Long changeStatus(ChangeStatusDto dto);

    Long completeDeal(CompleteDealDto dto);

    Long confirmComplete(ConfirmDealDto dto);

    Long forceCloseDeal(ForceCloseDealDto dto);

    Long confirmCloseDeal(ConfirmDealDto dto);
}
