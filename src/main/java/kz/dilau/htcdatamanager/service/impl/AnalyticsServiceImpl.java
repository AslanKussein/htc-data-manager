package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.RealPropertyAnalytics;
import kz.dilau.htcdatamanager.repository.AnalyticsRepository;
import kz.dilau.htcdatamanager.service.AnalyticsService;
import kz.dilau.htcdatamanager.web.dto.AnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    @Override
    public ResultDto saveAnalytics(AnalyticsDto analyticsDto) {
        RealPropertyAnalytics analytics = analyticsRepository.findByBuildingIdAndDistrictId(analyticsDto.getBuildingId(), analyticsDto.getDistrictId());
        if (isNull(analytics)) {
            analytics = RealPropertyAnalytics.builder()
                    .buildingId(analyticsDto.getBuildingId())
                    .districtId(analyticsDto.getDistrictId())
                    .build();
        }
        analytics.setAveragePrice(analyticsDto.getAveragePrice());
        analyticsRepository.save(analytics);
        return ResultDto.builder()
                .success(true)
                .build();
    }
}
