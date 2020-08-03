package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import kz.dilau.htcdatamanager.web.rest.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.KEYCLOAK_REST_ENDPOINT)
@RequiredArgsConstructor
@Slf4j
public class KeycloakResource {
    private final KeycloakService keycloakService;

    @GetMapping("/readUserInfos")
    public ResponseEntity readUserInfos(@RequestParam(value = "loginList") List<String> logins) {
        ListResponse<UserInfoDto> result = keycloakService.readUserInfos(logins);
        return ApiResponse.OK(result);
    }

    @GetMapping("/readRole/{id}")
    public ResponseEntity readRole(@PathVariable("id") Long id) {
        RoleDto result = keycloakService.readRole(id);
        return ApiResponse.OK(result);
    }

    @GetMapping("/getContractForm/{id}/{contractType}")
    public ResponseEntity<ContractFormTemplateDto> getContractForm(@PathVariable("id") Long id,
                                                                   @PathVariable("contractType") String contractType) {
        return ResponseEntity.ok(keycloakService.getContractForm(id, contractType));
    }

    @PostMapping("/sendRealPropertyAnalyticsMessage")
    public ResponseEntity<ResultDto> sendRealPropertyAnalyticsMessage(@RequestBody RealPropertyAnalyticsDto realPropertyAnalyticsDto) {
        return ResponseEntity.ok(keycloakService.sendRealPropertyAnalyticsMessage(realPropertyAnalyticsDto));
    }

    @PostMapping("/sendAgentAnalyticsMessage")
    public ResponseEntity<ResultDto> sendAgentAnalyticsMessage(@RequestBody AgentAnalyticsDto agentAnalyticsDto) {
        return ResponseEntity.ok(keycloakService.sendAgentAnalyticsMessage(agentAnalyticsDto));
    }
}
