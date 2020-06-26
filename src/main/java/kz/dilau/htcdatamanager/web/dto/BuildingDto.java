package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Building;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

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
    @ApiModelProperty(value = "ID ЖК")
    private Long residentialComplexId;
    @ApiModelProperty(value = "Номер дома", required = true)
    private String houseNumber;
    @ApiModelProperty(value = "Дробь")
    private String houseNumberFraction;
    @ApiModelProperty(value = "Уникальный код казпочты", required = true)
    private String postcode;
    @ApiModelProperty(value = "Широта")
    private BigDecimal latitude;
    @ApiModelProperty(value = "Долгота")
    private BigDecimal longitude;

    public BuildingDto(Building building) {
        if (nonNull(building)) {
            this.cityId = building.getCity().getId();
            this.districtId = building.getDistrict().getId();
            this.streetId = building.getStreet().getId();
            this.houseNumber = building.getHouseNumber();
            this.houseNumberFraction = building.getHouseNumberFraction();
            this.postcode = building.getPostcode();
            this.latitude = building.getLatitude();
            this.longitude = building.getLongitude();
            if (nonNull(building.getResidentialComplex())) {
                this.residentialComplexId = building.getResidentialComplex().getId();
            }
        }
    }
}
