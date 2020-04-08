package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "application")
public class Application extends AuditableBaseEntity<String, Long> {
    @NotNull(message = "Operation type must not be null")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id", nullable = false)
    private OperationType operationType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_type_id", referencedColumnName = "id")
    private ObjectType objectType;
    @Column(name = "object_price")
    private Double objectPrice;
    @Column(name = "object_price_from")
    private Double objectPriceFrom;
    @Column(name = "object_price_to")
    private Double objectPriceTo;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "possible_reason_for_bidding_id", referencedColumnName = "id")
    private PossibleReasonForBidding possibleReasonForBidding;
    @Column(name = "contract_period")
    @Temporal(TemporalType.DATE)
    private Date contractPeriod;
    @Min(0)
    @Column(name = "the_amount_of_the_contract")
    private Integer amount;
    @NotNull(message = "Commission is included in the price must not be null")
    @Column(name = "is_commission_included_in_the_price", nullable = false)
    private boolean isCommissionIncludedInThePrice = false;
    @Column(name = "note")
    private String note;
    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private RealPropertyOwner owner;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "real_property_id", referencedColumnName = "id")
    private RealProperty realProperty;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_status_id", referencedColumnName = "id", nullable = false)
    private ApplicationStatus applicationStatus;
}
