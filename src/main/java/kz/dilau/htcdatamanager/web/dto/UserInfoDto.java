package kz.dilau.htcdatamanager.web.dto;

import lombok.Data;

import java.util.List;

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

    public String getFullname() {
        return surname + " " + name;
    }
}
