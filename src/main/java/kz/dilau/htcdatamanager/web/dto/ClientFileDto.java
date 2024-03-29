package kz.dilau.htcdatamanager.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientFileDto {
    private Long id;
    private Long personClientId;
    private String guid;
}
