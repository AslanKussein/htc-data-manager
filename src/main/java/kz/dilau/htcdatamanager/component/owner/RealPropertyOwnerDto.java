package kz.dilau.htcdatamanager.component.owner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@ApiModel(description = "Модель сущности Клиент")
@NoArgsConstructor
@Getter
@Setter
public class RealPropertyOwnerDto {
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
    @ApiModelProperty(value = "Номер телефона", example = "+7-***-***-**-**")
    @NotNull(message = "Phone number must not be null")
//    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number should be valid")
    private String phoneNumber;
    @ApiModelProperty(value = "Адрес электронной почты")
    @Email(message = "Email should be valid")
    private String email;
    @ApiModelProperty(value = "Пол клиента")
    private Gender gender = Gender.UNKNOWN;

    public RealPropertyOwnerDto(RealPropertyOwner owner) {
        this.id = owner.getId();
        this.firstName = owner.getFirstName();
        this.surname = owner.getSurname();
        this.patronymic = owner.getPatronymic();
        this.phoneNumber = owner.getPhoneNumber();
        this.email = owner.getEmail();
        this.gender = owner.getGender();
    }
}
