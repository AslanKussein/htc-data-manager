package kz.dilau.htcdatamanager.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property_owner")
public class RealPropertyOwner extends AuditableBaseEntity<String, Long> {
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @NotNull(message = "Phone number must not be null")
//    @Pattern(
//            regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",//todo regex
//            message = "Mobile number should be valid"
//    )
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender = Gender.UNKNOWN;

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}
