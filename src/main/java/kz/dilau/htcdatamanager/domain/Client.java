package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;
import static kz.dilau.htcdatamanager.util.StringUtils.mapFullName;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

    @OneToMany(mappedBy = "client" ,cascade = CascadeType.ALL)
    private List<ClientPhoneNumber> clientPhoneNumberList;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<ClientFile> clientFileList;
    private String location;
    private ZonedDateTime birthDate;

    public List<ClientPhoneNumber> getClientPhoneNumberList() {
        if (isNull(clientPhoneNumberList)) {
            clientPhoneNumberList = new ArrayList<>();
        }
        return clientPhoneNumberList;
    }
    public List<ClientFile> getClientFileList() {
        if (isNull(clientFileList)) {
            clientFileList = new ArrayList<>();
        }
        return clientFileList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Transient
    public String getFullname() {
        return mapFullName(this.surname, this.firstName, this.patronymic);
    }

}
