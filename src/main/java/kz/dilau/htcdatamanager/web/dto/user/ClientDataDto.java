package kz.dilau.htcdatamanager.web.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDataDto {
    @ApiModelProperty(value = "Организация выдавшая документ")
    private String docOrg;
    @ApiModelProperty(value = "№ документа")
    private String docNumber;
    @ApiModelProperty(value = "Дата документа")
    private ZonedDateTime docDate;

    @ApiModelProperty(value = "Местонахождения")
    private Long location;
}
