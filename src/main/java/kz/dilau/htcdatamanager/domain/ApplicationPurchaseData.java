package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
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
    @JoinColumn(name = "purchase_info_id")
    private PurchaseInfo purchaseInfo;
    @Column(name = "purchase_info_id", insertable = false, updatable = false)
    private Long purchaseInfoId;

    //    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = TABLE_NAME_PREFIX + "purchase_data_possible_reason_for_bidding",
//            joinColumns = @JoinColumn(name = "purchase_data_id"),
//            inverseJoinColumns = @JoinColumn(name = "possible_reason_for_bidding_id")
//    )
    @Type(type = "jsonb")
    @Column(name = "possible_reasons_for_bidding", columnDefinition = "jsonb")
    private Set<IdItem> possibleReasonsForBidding = new HashSet<>();

    public Set<IdItem> getPossibleReasonsForBidding() {
        if (isNull(possibleReasonsForBidding)) {
            possibleReasonsForBidding = new HashSet<>();
        }
        return possibleReasonsForBidding;
    }
}
