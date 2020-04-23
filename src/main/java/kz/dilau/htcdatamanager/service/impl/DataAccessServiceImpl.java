package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.service.DataAccessService;
import kz.dilau.htcdatamanager.web.dto.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataAccessServiceImpl implements DataAccessService {
    private final RestTemplate restTemplate;
    private final DataProperties dataProperties;

    @Override
    public ListResponse<CheckOperationGroupDto> getCheckOperationList(final String token, List<String> groupCodes) {
        final String url = dataProperties.getKeycloakRoleManagerUrl() + "operations/check";
        System.out.println("token: " + token);
        System.out.println("URL: " + url);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for (String groupCode : groupCodes) {
            uriBuilder.queryParam("groupCodes", groupCode);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<ListResponse<CheckOperationGroupDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ListResponse<CheckOperationGroupDto>>() {
                }
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        return response.getBody();
    }
}
