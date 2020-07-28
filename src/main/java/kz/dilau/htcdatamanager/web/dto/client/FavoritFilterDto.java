package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FavoritFilterDto extends PageableDto {
    @ApiModelProperty(name = "ID устройства")
    private String deviceUuid;

    @ApiModelProperty(name = "ID недвижимости")
    private Long realPropertyId;
}
