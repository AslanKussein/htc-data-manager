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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_type_id")
    private PayType payType;
    @Column(name = "pay_type_id", insertable = false, updatable = false)
    private Long payTypeId;
    @Column(name = "payed_sum")
    private BigDecimal payedSum;
    @Column(name = "payed_client_login")
    private String payedClientLogin;
    @Column(name = "is_payed")
    private Boolean isPayed;

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
                                   City city, District district, MaterialOfConstruction materialOfConstruction, YardType yardType) {
        this.city = city;
        this.district = district;
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
            this.purchaseInfo = new PurchaseInfo(infoDto, dataDto.getObjectPricePeriod(), materialOfConstruction, yardType);
        }
    }

    public ApplicationPurchaseData(ApplicationPurchaseDataDto dataDto, City city, District district) {
        this.city = city;
        this.district = district;
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
    }


    public ApplicationPurchaseData(Application application, PurchaseInfoClientDto dataDto, PurchaseInfo purchaseInfo, City city, District district, PayType payType) {
        this.city = city;
        this.district = district;
        this.mortgage = dataDto.getMortgage();
        this.probabilityOfBidding = dataDto.getProbabilityOfBidding();
        this.note = dataDto.getNote();
        this.purchaseInfo = purchaseInfo;
        this.application = application;
        this.payTypeId = dataDto.getPayTypeId();
        this.payType = payType;
        this.payedSum = dataDto.getPayedSum();
        this.isPayed = dataDto.getIsPayed();
        this.payedClientLogin = dataDto.getPayedClientLogin();
        if (nonNull(application) && nonNull(application.getApplicationPurchaseData())) {
            this.id = application.getApplicationPurchaseData().getId();
        }
    }

    public ApplicationPurchaseData(Application application, String note) {
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
