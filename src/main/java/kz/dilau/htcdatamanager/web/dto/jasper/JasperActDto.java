package kz.dilau.htcdatamanager.web.dto.jasper;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JasperActDto {
    private String fDate;

    private String fFullname;

    private String fCustsign;

    private String fSuppliersign;
}
