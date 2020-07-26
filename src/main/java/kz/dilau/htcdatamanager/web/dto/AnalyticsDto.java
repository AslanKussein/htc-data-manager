package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyAnalytics;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AnalyticsDto", description = "Модель аналитики цены объекта недвижимости")
public class AnalyticsDto {
    @NonNull
    @ApiModelProperty(value = "Средняя цена за квадратный метр")
    private BigDecimal averagePrice;
    @NonNull
    @ApiModelProperty(value = "ID района")
    private Long districtId;
    @NonNull
    @ApiModelProperty(value = "ID здания")
    private Long buildingId;
    @NonNull
    @ApiModelProperty(value = "ID класса жилья")
    private Long houseClassId;

    public AnalyticsDto(RealPropertyAnalytics analytics) {
        if (nonNull(analytics)) {
            this.averagePrice = analytics.getAveragePrice();
            this.buildingId = analytics.getBuildingId();
            this.districtId = analytics.getDistrictId();
            this.houseClassId = analytics.getHouseClassId();
        }
    }
}
