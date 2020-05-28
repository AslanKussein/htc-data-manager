package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.AgentDto;
import kz.dilau.htcdatamanager.web.dto.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.web.dto.UserInfoDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

import java.util.List;
import java.util.Map;

public interface KeycloakService {
    Map<String, UserInfoDto> mapUserInfos(List<String> login);

    ListResponse<UserInfoDto> readUserInfos(List<String> login);

    ListResponse<AgentDto> getAgents(String token);

    ListResponse<CheckOperationGroupDto> getCheckOperationList(String token, List<String> groupCodes);
}
