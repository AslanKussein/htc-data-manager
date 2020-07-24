package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

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
}
