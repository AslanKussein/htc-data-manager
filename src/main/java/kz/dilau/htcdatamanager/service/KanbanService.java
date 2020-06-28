package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.*;

public interface KanbanService {
    Long changeStatus(ChangeStatusDto dto);

    Long completeDeal(CompleteDealDto dto);

    Long confirmComplete(ConfirmDealDto dto);

    Long forceCloseDeal(ForceCloseDealDto dto);

    Long confirmCloseDeal(ConfirmDealDto dto);

    CompleteApplicationDto applicationInfo(Long applicationId);
}
