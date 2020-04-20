package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "application")
public class Application extends AuditableBaseEntity<String, Long> {
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;
    @Column(name = "object_price")
    private BigDecimal objectPrice;
    @Column(name = "mortgage")
    private Boolean mortgage;//ипотека
    @Column(name = "encumbrance")
    private Boolean encumbrance;//обременение
    @Column(name = "is_shared_ownership_property")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @Column(name = "is_exchange")
    private Boolean exchange;//обмен
    @Column(name = "has_probability_of_bidding")
    private Boolean probabilityOfBidding;//вероятность торга
    @Column(name = "the_size_of_trades")
    private String theSizeOfTrades;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = TABLE_NAME_PREFIX + "application_possible_reason_for_bidding",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "possible_reason_for_bidding_id")
    )
    private Set<PossibleReasonForBidding> possibleReasonsForBidding = new HashSet<>();
    @Column(name = "contract_period")
    @Temporal(TemporalType.DATE)
    private Date contractPeriod;
    @Column(name = "the_amount_of_the_contract")
    private BigDecimal amount;
    @Column(name = "is_commission_included_in_the_price", nullable = false)
    private boolean isCommissionIncludedInThePrice = false;
    @Column(name = "note")
    private String note;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private RealPropertyOwner owner;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;
    @ManyToOne(optional = false)
    @JoinColumn(name = "application_status_id", nullable = false)
    private ApplicationStatus applicationStatus;
}
