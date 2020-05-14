package kz.dilau.htcdatamanager.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPhoneNumbersDto {
    private Long id;
    private Long clientId;
    private String phoneNumber;


}
