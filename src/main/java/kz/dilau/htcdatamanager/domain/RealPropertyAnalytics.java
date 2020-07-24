package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.math.BigDecimal;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property_analytics")
public class RealPropertyAnalytics extends BaseEntity<Long> {
    @Column(name = "average_price")
    private BigDecimal averagePrice;

    @Column(name = "building_id")
    private Long buildingId;

    @Column(name = "district_id")
    private Long districtId;
}
