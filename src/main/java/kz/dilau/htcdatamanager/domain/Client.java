package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "client")
public class Client extends AuditableBaseEntity<String, Long> {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;
    @OneToMany(mappedBy = "client")
    private List<Application> applicationList;

    public List<Application> getApplicationList() {
        if (isNull(applicationList)) {
            applicationList = new ArrayList<>();
        }
        return applicationList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Transient
    public Application getLastApplication() {
        return getApplicationList().stream().max(Comparator.comparing(Application::getCreatedDate)).orElseGet(null);
    }
}
