package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.PayType;
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
@Table(name = TABLE_NAME_PREFIX + "application_deposit")
public class ApplicationDeposit extends AuditableBaseEntity<String, Long> {
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(name = "application_id", insertable = false, updatable = false)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "sell_application_id")
    private Application sellApplication;

    @Column(name = "sell_application_id", insertable = false, updatable = false)
    private Long sellApplicationId;

    @Column(name = "payed_sum")
    private BigDecimal payedSum;

    @Column(name = "payed_client_login")
    private String payedClientLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_type_id")
    private PayType payType;

    @Column(name = "pay_type_id", insertable = false, updatable = false)
    private Long payTypeId;

    @Column(name = "print_date")
    private ZonedDateTime printDate;

    @Column(name = "guid")
    private String guid;

    @Column(name = "contract_number")
    private String contractNumber;
}
