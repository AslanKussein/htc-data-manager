package kz.dilau.htcdatamanager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    private Long id;
    private String nameRu;
    private String nameKk;
    private String nameEn;
}
