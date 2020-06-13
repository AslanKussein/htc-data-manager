package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

import java.util.List;
import java.util.Map;

public interface KeycloakService {
    Map<String, UserInfoDto> mapUserInfos(List<String> login);

    ListResponse<UserInfoDto> readUserInfos(List<String> login);

    ListResponse<AgentDto> getAgents(String token);

    RoleDto readRole(Long id);

    ListResponse<CheckOperationGroupDto> getCheckOperationList(String token, List<String> groupCodes);

    List<ProfileClientDto> readClientInfoByLogins(List<String> logins);
}
