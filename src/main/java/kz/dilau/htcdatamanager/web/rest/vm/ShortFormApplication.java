package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ShortFormApplication {
    private Long clientId;
    @NotNull
    @Pattern(
            regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",
            message = "Mobile number is invalid"
    )//todo regex
    private String phoneNumber;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String surname;
    private String patronymic;
    @NotNull(message = "Operation type must not be null")
    @Min(1)
    private Long operationTypeId;
    private String note;
}
