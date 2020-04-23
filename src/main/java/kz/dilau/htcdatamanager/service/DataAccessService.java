package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

import java.util.List;

public interface DataAccessService {
    ListResponse<CheckOperationGroupDto> getCheckOperationList(final String token, List<String> groupCodes);
}
