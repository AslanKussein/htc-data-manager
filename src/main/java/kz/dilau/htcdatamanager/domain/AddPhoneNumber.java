package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = TABLE_NAME_PREFIX + "add_phone_number")
public class AddPhoneNumber extends AuditableBaseEntity<String, Long> {
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;
}