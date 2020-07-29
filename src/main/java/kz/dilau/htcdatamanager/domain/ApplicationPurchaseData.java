package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.web.dto.ApplicationPurchaseDataDto;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.client.PurchaseInfoClientDto;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
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
@Table(name = TABLE_NAME_PREFIX + "purchase_data")
public class ApplicationPurchaseData extends AApplicationData {
    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    @Column(name = "city_id", insertable = false, updatable = false)
    private Long cityId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = TABLE_NAME_PREFIX + "purchase_data_districts",
            joinColumns = @JoinColumn(name = "purchase_data_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id")
    )
    private Set<IdItem> districts = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_info_id")
    private PurchaseInfo purchaseInfo;
    @Column(name = "purchase_info_id", insertable = false, updatable = false)
    private Long purchaseInfoId;

    @Type(type = "jsonb")
    @Column(name = "possible_reasons_for_bidding", columnDefinition = "jsonb")
    private Set<IdItem> possibleReasonsForBidding = new HashSet<>();

    @Type(type = "jsonb")
    @Column(name = "application_flags", columnDefinition = "jsonb")
    private Set<IdItem> applicationFlags = new HashSet<>();

    public ApplicationPurchaseData(ApplicationPurchaseDataDto dataDto, PurchaseInfoDto infoDto,
                                   City city, MaterialOfConstruction materialOfConstruction,
                                   YardType yardType, Sewerage sewerage, HeatingSystem heatingSystem) {
        this.city = city;
        this.districts = dataDto.getDistricts()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.mortgage = dataDto.getMortgage();
        this.probabilityOfBidding = dataDto.getProbabilityOfBidding();
        this.theSizeOfTrades = dataDto.getTheSizeOfTrades();
        this.note = dataDto.getNote();
        this.possibleReasonsForBidding = dataDto.getPossibleReasonForBiddingIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.applicationFlags = dataDto.getApplicationFlagIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        if (nonNull(infoDto)) {
            this.purchaseInfo = new PurchaseInfo(infoDto, dataDto.getObjectPricePeriod(), materialOfConstruction, yardType, sewerage, heatingSystem);
        }
    }

    public ApplicationPurchaseData(ApplicationPurchaseDataDto dataDto, City city) {
        this.city = city;
        this.districts = dataDto.getDistricts()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.probabilityOfBidding = dataDto.getProbabilityOfBidding();
        this.theSizeOfTrades = dataDto.getTheSizeOfTrades();
        this.note = dataDto.getNote();
        this.possibleReasonsForBidding = dataDto.getPossibleReasonForBiddingIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.applicationFlags = dataDto.getApplicationFlagIdList()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
    }


    public ApplicationPurchaseData(Application application, PurchaseInfoClientDto dataDto, PurchaseInfo purchaseInfo, City city) {
        this.city = city;
        this.districts = dataDto.getDistricts()
                .stream()
                .map(IdItem::new)
                .collect(Collectors.toSet());
        this.mortgage = dataDto.getMortgage();
        this.probabilityOfBidding = dataDto.getProbabilityOfBidding();
        this.note = dataDto.getNote();
        this.purchaseInfo = purchaseInfo;
        this.application = application;
        if (nonNull(application) && nonNull(application.getApplicationPurchaseData())) {
            this.id = application.getApplicationPurchaseData().getId();
        }
    }

    public ApplicationPurchaseData(Application application, String note) {
        this.application = application;
        this.note = note;
    }

    public Set<IdItem> getDistricts() {
        if (isNull(districts)) {
            districts = new HashSet<>();
        }
        return districts;
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
