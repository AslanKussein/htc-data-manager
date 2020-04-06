package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.rest.vm.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.web.rest.vm.ListResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class DataAccessManager {
    //    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public DataAccessManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @Autowired
//    public DataAccessManager(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
//        this.discoveryClient = discoveryClient;
//        this.restTemplate = restTemplate;
//    }

    public Optional<URI> getRoleManagerUrl() {
//        return discoveryClient.getInstances("htc-role-manager")
//                .stream()
//                .map(si -> si.getUri())
//                .findFirst();
        return null;
    }

    public ListResponse<CheckOperationGroupDto> getCheckOperationList(final String token, List<String> groupCodes) {
        final String url = getRoleManagerUrl().get().toString() + "operations/check";
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
