package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.*;

import javax.validation.constraints.*;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сущности Клиент")
public class ClientDto {
    @ApiModelProperty(value = "ID клиента")
    @Min(1)
    private Long id;
    @ApiModelProperty(value = "Имя клиента")
    @NotBlank(message = "First name is required")
    private String firstName;
    @ApiModelProperty(value = "Фамилия клиента")
    private String surname;
    @ApiModelProperty(value = "Отчество клиента")
    private String patronymic;
    @ApiModelProperty(value = "Номер телефона", example = "7**********")
    @NotNull(message = "Phone number must not be null")
//    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number should be valid")
    private String phoneNumber;
    @ApiModelProperty(value = "Адрес электронной почты")
    @Email(message = "Email should be valid")
    private String email;
    @ApiModelProperty(value = "Пол клиента")
    private Gender gender = Gender.UNKNOWN;
    @ApiModelProperty(value = "местонахождения")
    private String location;
    @ApiModelProperty(value = "день рождения")
    private ZonedDateTime birthDate;

    public Gender getGender() {
        if (isNull(gender)) {
            gender = Gender.UNKNOWN;
        }
        return gender;
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.surname = client.getSurname();
        this.patronymic = client.getPatronymic();
        this.phoneNumber = client.getPhoneNumber();
        this.email = client.getEmail();
        this.gender = client.getGender();
        this.birthDate = client.getBirthDate();
        this.location = client.getLocation();
    }
}
