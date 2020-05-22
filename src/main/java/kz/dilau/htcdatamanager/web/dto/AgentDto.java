package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AgentDto", description = "Объект агента для назначения/переназначения")
public class AgentDto {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "Логин")
    private String login;
    @ApiModelProperty(value = "Фамилия")
    private String surname;
    @ApiModelProperty(value = "Имя")
    private String name;
    @ApiModelProperty(value = "Количество заявок в работе")
    private long applicationCount = 0;

    public AgentDto(String login, long applicationCount) {
        this.login = login;
        this.applicationCount = applicationCount;
    }

    public String getLogin() {
        if (nonNull(login)) {
            login = login.trim().toLowerCase();
        }
        return login;
    }
}
