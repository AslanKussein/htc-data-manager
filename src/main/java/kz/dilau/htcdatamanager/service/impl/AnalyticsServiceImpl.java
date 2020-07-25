package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.repository.AnalyticsRepository;
import kz.dilau.htcdatamanager.service.AnalyticsService;
import kz.dilau.htcdatamanager.web.dto.AnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    @Override
    public ResultDto saveAnalytics(AnalyticsDto dto) {
        RealPropertyAnalytics analytics = null;
        if (nonNull(dto.getBuildingId())) {
            analytics = analyticsRepository.findByBuildingId(dto.getBuildingId());
        } else if (nonNull(dto.getDistrictId()) && nonNull(dto.getHouseClassId())) {
            analytics = analyticsRepository.findByDistrictIdAndHouseClassId(dto.getDistrictId(), dto.getHouseClassId());
        }
        if (isNull(analytics)) {
            analytics = RealPropertyAnalytics.builder()
                    .buildingId(dto.getBuildingId())
                    .districtId(dto.getDistrictId())
                    .houseClassId(dto.getHouseClassId())
                    .build();
        }
        analytics.setAveragePrice(dto.getAveragePrice());
        analyticsRepository.save(analytics);
        return ResultDto.builder()
                .success(true)
                .build();
    }

    public AnalyticsDto getAnalytics(Application application) {
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData()) &&
                nonNull(application.getApplicationSellData().getRealProperty()) && nonNull(application.getApplicationSellData().getRealProperty().getBuilding())) {
            Building building = application.getApplicationSellData().getRealProperty().getBuilding();
            Long houseClassId = null;
            if (nonNull(building.getResidentialComplex()) && nonNull(building.getResidentialComplex().getGeneralCharacteristics())) {
                houseClassId = building.getResidentialComplex().getGeneralCharacteristics().getHouseClassId();
            }
            RealPropertyAnalytics analytics = analyticsRepository.findByBuildingId(building.getId());
            if (isNull(analytics)) {
                analytics = analyticsRepository.findByDistrictIdAndHouseClassId(building.getDistrictId(), houseClassId);
            }
            return new AnalyticsDto(analytics);
        }
        return null;
    }
}
