package kz.dilau.htcdatamanager.web.dto.user;

import lombok.Data;

import java.util.List;

import static kz.dilau.htcdatamanager.util.StringUtils.mapFullName;

@Data
public class UserInfoDto {
    private Long id;
    private String surname;
    private String patronymic;
    private String name;
    private String login;
    private String group;
    private String phone;
    private String email;
    private String iin;
    private List<String> roles;
    //private Long organizationId;
    //private OrganizationDto organizationDto;

    private EmployeeDataDto employeeData;
    private ClientDataDto clientData;
    private Long personId;
    private String photoUuid;

    public String getFullname() {
        return mapFullName(this.surname, this.name, this.patronymic);
    }

}
