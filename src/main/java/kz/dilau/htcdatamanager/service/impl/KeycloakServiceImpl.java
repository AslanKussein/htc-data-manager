package kz.dilau.htcdatamanager.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.exception.DetailedException;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.web.dto.AgentDto;
import kz.dilau.htcdatamanager.web.dto.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.web.dto.RoleDto;
import kz.dilau.htcdatamanager.web.dto.UserInfoDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
    private static final int REMAINING_SECONDS = 10;
    private static final String CLIENT_ID = "client_id";
    private static final String GRANT_TYPE = "grant_type";
    private static final String U_NAME = "username";
    private static final String P_NAME = "password";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN_ENDPOINT = "token";
    private static final String AGENT_REST_ENDPOINT = "/api/agents";
    private static final String USER_REST_ENDPOINT = "/api/users";
    private static final String OPERATIONS_REST_ENDPOINT = "/operations/check";
    private static final String USERS_INFO = "/infos";
    private static final String ROLE_REST_ENDPOINT = "/roles";

    private final RestTemplate restTemplate;
    private final DataProperties dataProperties;

    private ConcurrentMap<String, TokenInfo> tokenMap = new ConcurrentHashMap<>();

    public String getUserManagerToken() {
        return getAccessToken(
                dataProperties.getKeycloakUserManagerClient(),
                dataProperties.getKeycloakBaseUrl() + TOKEN_ENDPOINT,
                dataProperties.getKeycloakUserManagerLogin(),
                dataProperties.getKeycloakUserManagerPassword()
        );
    }

    public String getRoleManagerToken() {
        return getAccessToken(
                dataProperties.getKeycloakRoleManagerClient(),
                dataProperties.getKeycloakBaseUrl() + TOKEN_ENDPOINT,
                dataProperties.getKeycloakRoleManagerLogin(),
                dataProperties.getKeycloakRoleManagerPassword()
        );
    }

    private String getAccessToken(String clientId, String url, String username, String password) {
        TokenInfo tokenInfo = tokenMap.computeIfAbsent(clientId, key -> obtainToken(
                clientId, url, username, password
        ));
        LocalDateTime now = LocalDateTime.now();
        if (tokenInfo.getDate().plusSeconds(tokenInfo.getExpiresIn() - REMAINING_SECONDS).isAfter(now)) {
            return tokenInfo.getAccessToken();
        }
        if (tokenInfo.getDate().plusSeconds(tokenInfo.getRefreshExpiresIn() - REMAINING_SECONDS).isAfter(now)) {
            tokenInfo = tokenMap.computeIfPresent(clientId, (key, value) -> refreshToken(clientId, url, value));
            return tokenInfo.getAccessToken();
        }

        // both access_token and refresh_token are expired
        tokenMap.remove(clientId);
        return getAccessToken(clientId, url, username, password);
    }

    private TokenInfo obtainToken(String clientId, String url, String username, String password) {
        log.info("Get token for clientId = {}", clientId);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(CLIENT_ID, clientId);
        params.add(GRANT_TYPE, P_NAME);
        params.add(U_NAME, username);
        params.add(P_NAME, password);

        HttpEntity entity = new HttpEntity<>(params, new HttpHeaders());

        ResponseEntity<TokenInfo> response = invokeRestExchange(url, HttpMethod.POST, entity, TokenInfo.class);
        return response.getBody();
    }

    private TokenInfo refreshToken(String clientId, String url, TokenInfo tokenInfo) {
        log.info("Refresh token for clientId = {}", clientId);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(CLIENT_ID, clientId);
        params.add(GRANT_TYPE, REFRESH_TOKEN);
        params.add(REFRESH_TOKEN, tokenInfo.getRefreshToken());

        HttpEntity entity = new HttpEntity<>(params, new HttpHeaders());
        ResponseEntity<TokenInfo> response = invokeRestExchange(url, HttpMethod.POST, entity, TokenInfo.class);
        return response.getBody();
    }

    @Data
    private static class TokenInfo {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private int expiresIn;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("refresh_expires_in")
        private int refreshExpiresIn;

        private LocalDateTime date = LocalDateTime.now();
    }

    @Override
    public Map<String, UserInfoDto> mapUserInfos(List<String> logins) {
        Map<String, UserInfoDto> userMap = new HashMap<>();
        ListResponse<UserInfoDto> body = readUserInfos(logins);
        if (nonNull(body) && nonNull(body.getData()) && !body.getData().isEmpty()) {
            body.getData().forEach(user -> userMap.put(user.getLogin(), user));
        }
        return userMap;
    }

    @Override
    public ListResponse<UserInfoDto> readUserInfos(List<String> logins) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(logins, headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + USER_REST_ENDPOINT + USERS_INFO;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for (String login : logins) {
            uriBuilder.queryParam("login", login);
        }
        ResponseEntity<ListResponse<UserInfoDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ListResponse<UserInfoDto>>() {
                }
        );

        return response.getBody();
    }

    @Override
    public ListResponse<AgentDto> getAgents(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Object> request = new HttpEntity<>(token, headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + AGENT_REST_ENDPOINT;
        ResponseEntity<ListResponse<AgentDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ListResponse<AgentDto>>() {
                }
        );

        return response.getBody();
    }

    @Override
    public RoleDto readRole(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getRoleManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(id, headers);
        String url = dataProperties.getKeycloakRoleManagerUrl() + ROLE_REST_ENDPOINT;

        ResponseEntity<RoleDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<RoleDto>() {
                }
        );

        return response.getBody();
    }

    @Override
    public ListResponse<CheckOperationGroupDto> getCheckOperationList(String token, List<String> groupCodes) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = dataProperties.getKeycloakRoleManagerUrl() + OPERATIONS_REST_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for (String groupCode : groupCodes) {
            uriBuilder.queryParam("groupCodes", groupCode);
        }
        ResponseEntity<ListResponse<CheckOperationGroupDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ListResponse<CheckOperationGroupDto>>() {
                }
        );
        return response.getBody();
    }

    private <T> ResponseEntity<T> invokeRestExchange(String url, HttpMethod httpMethod, HttpEntity<?> request, Class<T> responseType) {
        try {
            return restTemplate.exchange(url, httpMethod, request, responseType);
        } catch (HttpClientErrorException e) {
            throw new DetailedException(e.getStatusCode().value(), e.getStatusCode().getReasonPhrase(), e.getMessage());
        } catch (RestClientException rce) {
            throw new DetailedException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), rce.getMessage());
        }
    }
}
