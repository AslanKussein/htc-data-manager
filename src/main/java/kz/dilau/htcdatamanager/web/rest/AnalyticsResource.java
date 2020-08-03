package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.AnalyticsService;
import kz.dilau.htcdatamanager.web.dto.RealPropertyAnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.ANALYTICS_REST_ENDPOINT)
public class AnalyticsResource {
    private final AnalyticsService analyticsService;

    @ApiIgnore
    @PostMapping
    public ResponseEntity<ResultDto> saveAnalytics(@RequestBody RealPropertyAnalyticsDto dto) {
        ResultDto result = analyticsService.saveAnalytics(dto);
        return ResponseEntity.ok(result);
    }

    @ApiIgnore
    @GetMapping("/{appId}")
    public ResponseEntity<RealPropertyAnalyticsDto> getAnalytics(@PathVariable("appId") Long appId) {
        RealPropertyAnalyticsDto result = analyticsService.getAnalytics(appId);
        return ResponseEntity.ok(result);
    }
}
