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
@ApiModel(value = "RealPropertyAnalyticsDto", description = "Модель аналитики цены объекта недвижимости")
public class RealPropertyAnalyticsDto {
    @NonNull
    @ApiModelProperty(value = "Средняя цена за квадратный метр")
    private BigDecimal averagePrice;
    @ApiModelProperty(value = "ID района")
    private Long districtId;
    @ApiModelProperty(value = "ID здания")
    private Long buildingId;
    @ApiModelProperty(value = "ID класса жилья")
    private Long houseClassId;

    public RealPropertyAnalyticsDto(RealPropertyAnalytics analytics) {
        if (nonNull(analytics)) {
            this.averagePrice = analytics.getAveragePrice();
            this.buildingId = analytics.getBuildingId();
            this.districtId = analytics.getDistrictId();
            this.houseClassId = analytics.getHouseClassId();
        }
    }
}
