package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "deposit_numb")
public class DepositNumb extends AuditableBaseEntity<String, Long> {

    @Column(name = "code")
    private String code;

    @Column(name = "nmb")
    private Long nmb;
}
