package kz.dilau.htcdatamanager.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private String nameRu;
    private String nameKk;
    private String nameEn;
    private String descriptionRu;
    private String descriptionKk;
    private String descriptionEn;
    private List<String> operations;
}
