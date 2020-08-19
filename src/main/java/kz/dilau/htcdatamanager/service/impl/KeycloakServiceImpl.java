package kz.dilau.htcdatamanager.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.DetailedException;
import kz.dilau.htcdatamanager.service.kafka.KafkaProducer;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.util.ObjectSerializer;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.client.ClientDeviceDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.isNull;
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
    private static final String OPERATIONS_BY_ROLE_REST_ENDPOINT = "/operations/checkRolesAndOperations";
    private static final String USERS_INFO = "/infos";
    private static final String USER_INFO = "/info";
    private static final String ROLE_REST_ENDPOINT = "/roles/{id}";
    private static final String PROFILE_CLIENT_REST_ENDPOINT = "/api/profile-client";
    private static final String CLIENTS_BY_LOGINS = "/getList";
    private static final String GET_CONTRACT_FORM = "/api/organizations/getContractForm";
    private static final String UPLOAD_FILE_ENDPOINT = "/api/upload";
    private static final String DOWNLOAD_FILE_ENDPOINT = "/open-api/download";
    private static final String PROFILE_CONFIG_ENDPOINT = "/api/profile-config";
    private static final String PROFILE_CONFIG_OPEN_ENDPOINT = "/open-api/profile-config";
    private static final String PROFILE_CLIENT_OPEN_ENDPOINT = "/open-api/profile-client";
    private static final String REPLACE_DEVICE_LINK = "/api/profile-config/replaceDeviceLink/{deviceUuid}";

    private final RestTemplate restTemplate;
    private final DataProperties dataProperties;
    private final KafkaProducer kafkaProducer;

    private ConcurrentMap<String, TokenInfo> tokenMap = new ConcurrentHashMap<>();

    public String getUserManagerToken() {
        return getAccessToken(
                dataProperties.getKeycloakUserManagerClient(),
                dataProperties.getKeycloakBaseUrl() + TOKEN_ENDPOINT,
                dataProperties.getKeycloakUserManagerLogin(),
                dataProperties.getKeycloakUserManagerPassword()
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
    public UserInfoDto readUserInfo(String login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(login, headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + USER_REST_ENDPOINT + USER_INFO;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        uriBuilder.queryParam("login", login);
        ResponseEntity<UserInfoDto> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<UserInfoDto>() {
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
        headers.setBearerAuth(getUserManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = dataProperties.getKeycloakRoleManagerUrl() + ROLE_REST_ENDPOINT;
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<RoleDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<RoleDto>() {
                },
                params
        );

        return response.getBody();
    }

    @Override
    public List<String> getOperations(String token, List<String> groups) {
        List<String> operations = new ArrayList<>();
        ListResponse<CheckOperationGroupDto> checkOperationList = getCheckOperationList(token, groups);
        if (nonNull(checkOperationList) && nonNull(checkOperationList.getData())) {
            checkOperationList
                    .getData()
                    .stream()
                    .filter(operation -> nonNull(operation.getOperations()) && !operation.getOperations().isEmpty())
                    .map(CheckOperationGroupDto::getOperations)
                    .forEach(operations::addAll);
        }
        return operations;
    }

    @Override
    public ListResponse<String> getOperationsByRole(String token, OperationFilterDto filterDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Object> request = new HttpEntity<>(filterDto, headers);
        String url = dataProperties.getKeycloakRoleManagerUrl() + OPERATIONS_BY_ROLE_REST_ENDPOINT;
        ResponseEntity<ListResponse<String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ListResponse<String>>() {
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

    @Override
    public List<ProfileClientDto> readClientInfoByLogins(List<String> logins) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(logins, headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + PROFILE_CLIENT_REST_ENDPOINT + CLIENTS_BY_LOGINS;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        uriBuilder.queryParam("loginList", logins);
        ResponseEntity<List<ProfileClientDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProfileClientDto>>() {
                }
        );

        return response.getBody();
    }

    private class MultipartByteArrayResource extends ByteArrayResource {

        private String fileName;

        public MultipartByteArrayResource(byte[] byteArray, String fileName) {
            super(byteArray);
            this.setFilename(fileName);
        }

        public String getFilename() {
            return fileName;
        }

        public void setFilename(String fileName) {
            this.fileName= fileName;
        }

    }

    @Override
    public FileInfoDto uploadFile(String token, byte[] pFile, String pFilename) {
        HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth(token);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String url = dataProperties.getKeycloakFileManagerUrl() + UPLOAD_FILE_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        RestTemplate restTemplate = new RestTemplate();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new MultipartByteArrayResource(pFile, pFilename));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        return restTemplate
                .postForObject(uriBuilder.toUriString(), requestEntity, FileInfoDto.class);
    }



    public Resource getFile(String uuid) {
        HttpHeaders headers = new HttpHeaders();
        String url = dataProperties.getKeycloakFileManagerUrl() + DOWNLOAD_FILE_ENDPOINT + "/" + uuid;
        HttpEntity<Object> request = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Resource> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                Resource.class
        );
        return response.getBody();
    }

    @Override
    public void saveClient(ProfileClientDto p) {
        HttpHeaders headers = new HttpHeaders();
        String url = dataProperties.getKeycloakUserManagerUrl() + PROFILE_CLIENT_REST_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<Object> request = new HttpEntity<>(p, headers);
        restTemplate.postForObject(uriBuilder.toUriString(), request, ResponseEntity.class);
    }

    @Override
    public ResultDto sendRealPropertyAnalyticsMessage(RealPropertyAnalyticsDto realPropertyAnalyticsDto) {
        kafkaProducer.sendMessage(dataProperties.getTopicRealProperty(), ObjectSerializer.introspect(realPropertyAnalyticsDto));
        return new ResultDto();
    }

    @Override
    public ResultDto sendAgentAnalyticsMessage(AgentAnalyticsDto agentAnalyticsDto) {
        kafkaProducer.sendMessage(dataProperties.getTopicAnalyticAgent(), ObjectSerializer.introspect(agentAnalyticsDto));
        return new ResultDto();
    }

    @Override
    public List<ClientDeviceDto> getDevices(String token, String deviceUuid) {
        if (isNull(token) && isNull(deviceUuid)) {
            throw BadRequestException.createRequiredIsEmpty("deviceUuid");
        }
        HttpHeaders headers = new HttpHeaders();
        if (nonNull(token)) {
            headers.add(HttpHeaders.AUTHORIZATION, token);
        }
        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + (isNull(token) ? PROFILE_CONFIG_OPEN_ENDPOINT : PROFILE_CONFIG_ENDPOINT) + "/getDevice" + (nonNull(deviceUuid) ? "/" +deviceUuid : "");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<List<ClientDeviceDto>> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ClientDeviceDto>>() {}
        );
        return response.getBody();
    }

    @Override
    public ContractFormTemplateDto getContractForm(Long id, String contractType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserManagerToken());
        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + GET_CONTRACT_FORM;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        uriBuilder.queryParam("id", id);
        uriBuilder.queryParam("contractType", contractType);
        ResponseEntity<ContractFormTemplateDto> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ContractFormTemplateDto>() {
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

    @Override
    public ResultDto replaceUMDeviceLink(String token, String deviceUuid) {
        if (isNull(deviceUuid)) {
            throw BadRequestException.createRequiredIsEmpty("deviceUuid");
        }
        if (isNull(token)) {
            throw BadRequestException.createRequiredIsEmpty("token");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = dataProperties.getKeycloakUserManagerUrl() + REPLACE_DEVICE_LINK;
        ResponseEntity<ResultDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ResultDto>() {
                },
                deviceUuid
        );

        return response.getBody();
    }
}
