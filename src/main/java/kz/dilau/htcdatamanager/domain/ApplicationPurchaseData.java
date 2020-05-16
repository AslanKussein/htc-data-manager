package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import lombok.*;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "purchase_data")
public class ApplicationPurchaseData extends AApplicationData {
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
    private NewPurchaseInfo purchaseInfo;
    @Column(name = "purchase_info_id", insertable = false, updatable = false)
    private Long purchaseInfoId;


}
