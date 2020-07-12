package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientViewDto;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationForNotificationDto", description = "Модель для сервис уведомлений")
public class ApplicationForNotificationDto {

    private Long id;
    private String clientLogin;
    private String agent;
    private Long realPropertyId;
    private MultiLangText address;
    private ContractFormForNotificationDto contract;


}
