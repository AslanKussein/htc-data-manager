package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "sell_data")
public class ApplicationSellData extends AApplicationData {
    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "real_property_id")
    @ToString.Exclude
    private RealProperty realProperty;
    @Column(name = "object_price")
    private BigDecimal objectPrice;
    @Column(name = "encumbrance")
    private Boolean encumbrance;//обременение
    @Column(name = "is_shared_ownership_property")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @Column(name = "is_exchange")
    private Boolean exchange;//обмен
    @Column(name = "description")
    private String description;

    @Type(type = "jsonb")
    @Column(name = "possible_reasons_for_bidding", columnDefinition = "jsonb")
    private Set<IdItem> possibleReasonsForBidding = new HashSet<>();

    @Type(type = "jsonb")
    @Column(name = "application_flags", columnDefinition = "jsonb")
    private Set<IdItem> applicationFlags = new HashSet<>();

    public ApplicationSellData(ApplicationSellDataDto dataDto) {
        this.objectPrice = dataDto.getObjectPrice();
        this.encumbrance = dataDto.getEncumbrance();
        this.sharedOwnershipProperty = dataDto.getSharedOwnershipProperty();
        this.exchange = dataDto.getExchange();
        this.description = dataDto.getDescription();
        this.possibleReasonsForBidding = dataDto.getPossibleReasonForBiddingIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.applicationFlags= dataDto.getApplicationFlagIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.mortgage = dataDto.getMortgage();
        this.probabilityOfBidding = dataDto.getProbabilityOfBidding();
        this.theSizeOfTrades = dataDto.getTheSizeOfTrades();
        this.note = dataDto.getNote();
    }

    public ApplicationSellData(Application application, String note) {
        this.application = application;
        this.note = note;
    }

    public Set<IdItem> getPossibleReasonsForBidding() {
        if (isNull(possibleReasonsForBidding)) {
            possibleReasonsForBidding = new HashSet<>();
        }
        return possibleReasonsForBidding;
    }

    public Set<IdItem> getApplicationFlags() {
        if (isNull(applicationFlags)) {
            applicationFlags = new HashSet<>();
        }
        return applicationFlags;
    }
}
