package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MetadataWithApplicationsDto", description = "Данные по недвижимости")
public class MetadataWithApplicationsDto {
    @ApiModelProperty(value = "Данные по недвижимости")
    private RealPropertyDto realPropertyDto;

    @ApiModelProperty(value = "Список заявок по данной недвижимости")
    private List<ApplicationByRealPropertyDto> applicationByRealPropertyDtoList;
}
