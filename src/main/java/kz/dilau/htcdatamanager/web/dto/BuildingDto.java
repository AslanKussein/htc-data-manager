package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Building;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BuildingDto", description = "Модель здания/строения")
public class BuildingDto {
    @ApiModelProperty(value = "ID города", required = true)
    private Long cityId;
    @ApiModelProperty(value = "ID района", required = true)
    private Long districtId;
    @ApiModelProperty(value = "ID улицы", required = true)
    private Long streetId;
    @ApiModelProperty(value = "Номер дома", required = true)
    private Integer houseNumber;
    @ApiModelProperty(value = "Дробь")
    private String houseNumberFraction;
    @ApiModelProperty(value = "Уникальный код казпочты", required = true)
    private String postcode;
    @ApiModelProperty(value = "Широта")
    private BigDecimal latitude;
    @ApiModelProperty(value = "Долгота")
    private BigDecimal longitude;

    public BuildingDto(Building building) {
        this.cityId = building.getCityId();
        this.districtId = building.getDistrictId();
        this.streetId = building.getStreetId();
        this.houseNumber = building.getHouseNumber();
        this.houseNumberFraction = building.getHouseNumberFraction();
        this.postcode = building.getPostcode();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
    }
}
