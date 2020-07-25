package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.AnalyticsService;
import kz.dilau.htcdatamanager.web.dto.AnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.ANALYTICS_REST_ENDPOINT)
public class AnalyticsResource {
    private final AnalyticsService analyticsService;

    @ApiIgnore
    @PostMapping
    public ResponseEntity<ResultDto> saveAnalytics(@RequestBody AnalyticsDto dto) {
        ResultDto result = analyticsService.saveAnalytics(dto);
        return ResponseEntity.ok(result);
    }
}
