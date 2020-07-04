package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ContractStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ContractType;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "application_contract")
public class ApplicationContract extends AuditableBaseEntity<String, Long> {
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(name = "application_id", insertable = false, updatable = false)
    private Long applicationId;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "contract_period")
    private ZonedDateTime contractPeriod;

    @Column(name = "contract_sum")
    private BigDecimal contractSum;

    @Column(name = "commission")
    private BigDecimal commission;

    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;

    @Column(name = "contract_type_id", insertable = false, updatable = false)
    private Long contractTypeId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ContractStatus contractStatus;

    @Column(name = "print_date")
    private ZonedDateTime printDate;

    @Column(name = "guid")
    private String guid;

    @Column(name = "file_guid")
    private String fileGuid;
}
