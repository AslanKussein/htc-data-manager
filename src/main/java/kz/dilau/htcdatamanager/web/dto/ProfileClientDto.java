package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.List;

import static kz.dilau.htcdatamanager.util.StringUtils.mapFullName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileClientDto {
    @ApiModelProperty(value = "ФИО")
    private String surname;
    @ApiModelProperty(value = "Отчество")
    private String patronymic;
    @ApiModelProperty(value = "Имя")
    private String firstName;

    @ApiModelProperty(value = "ИИН клиента")
    private String iin;

    @ApiModelProperty(value = "Организация выдавшая документ")
    private String docOrg;
    @ApiModelProperty(value = "№ документа")
    private String docNumber;
    @ApiModelProperty(value = "Дата документа")
    private ZonedDateTime docDate;
    @ApiModelProperty(value = "Адрес")
    private String address;

    @ApiModelProperty(value = "День рождения")
    private ZonedDateTime birthDate;
    @ApiModelProperty(value = "Местонахождения")
    private Long location;
    @ApiModelProperty(value = "Номер телефона")
    private String phoneNumber;
    @ApiModelProperty(value = "Почта")
    private String email;
    @ApiModelProperty(value = "Активен ли юзер")
    private Boolean isActive;
    @ApiModelProperty(value = "Пол")
    private Gender gender;
    @ApiModelProperty(value = "Пароль")
    private String password;
    @ApiModelProperty(value = "id клиента")
    private Long id;
    @ApiModelProperty(value = "фото клиента")
    private String photoUuid;
    @ApiModelProperty(value = "доп телефоны клиента")
    private List<ClientPhoneNumberDto> clientPhoneNumbersDtoList;
    @ApiModelProperty(value = "файлы клиента")
    private List<ClientFileDto> clientFileDtoList;

    @Transient
    public String getFullname() {
        return mapFullName(this.surname, this.firstName, this.patronymic);
    }
}
