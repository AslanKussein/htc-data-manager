package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "История статусов заявки")
public class ApplicationStatusHistoryDto {
    @ApiModelProperty(value = "Cтатус")
    private MultiLangText applicationStatus;
    @ApiModelProperty(value = "Комментарий")
    private String comment;
    @ApiModelProperty(value = "Дата")
    private ZonedDateTime creationDate;
}
