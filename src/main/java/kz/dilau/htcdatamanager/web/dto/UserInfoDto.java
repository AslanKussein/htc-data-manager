package kz.dilau.htcdatamanager.web.dto;

import lombok.Data;

import java.util.List;

import static kz.dilau.htcdatamanager.util.StringUtils.mapFullName;

@Data
public class UserInfoDto {
    private Long id;
    private String surname;
    private String name;
    private String login;
    private String group;
    private String phone;
    private String email;
    private List<String> roles;
    private OrganizationDto organizationDto;

    public String getFullname() {
        return mapFullName(this.surname, this.name, null);
    }
}
