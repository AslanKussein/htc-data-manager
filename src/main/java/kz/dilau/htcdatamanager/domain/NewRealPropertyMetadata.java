package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "new_real_property_metadata")
public class NewRealPropertyMetadata extends AuditableBaseEntity<String, Long> {
    @ManyToOne
    @JoinColumn(name = "real_property_id")
    private NewRealProperty realProperty;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private NewApplication application;

    @Column(name = "floor")
    private Integer floor;
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;
    @Column(name = "number_of_bedrooms")
    private Integer numberOfBedrooms;
    @Column(name = "total_area")
    private BigDecimal totalArea;
    @Column(name = "living_area")
    private BigDecimal livingArea;
    @Column(name = "kitchen_area")
    private BigDecimal kitchenArea;
    @Column(name = "balcony_area")
    private BigDecimal balconyArea;
    @Column(name = "atelier")
    private Boolean atelier;//студия
    @Column(name = "separate_bathroom")
    private Boolean separateBathroom;
    @ManyToOne
    @JoinColumn(name = "house_condition_id", insertable = false, updatable = false)
    private HouseCondition houseCondition;
    @Column(name = "house_condition_id")
    private Long houseConditionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_complex_id")
    private NewResidentialComplex residentialComplex;
    @Column(name = "residential_complex_id", insertable = false, updatable = false)
    private Long residentialComplexId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sewerage_id")
    private Sewerage sewerage;
    @Column(name = "sewerage_id", insertable = false, updatable = false)
    private Long sewerageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heating_system_id")
    private HeatingSystem heatingSystem;
    @Column(name = "heating_system_id", insertable = false, updatable = false)
    private Long heatingSystemId;
    @Column(name = "land_area")
    private BigDecimal landArea;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "general_characteristics_id")
    private NewGeneralCharacteristics generalCharacteristics;
    @Column(name = "general_characteristics_id", insertable = false, updatable = false)
    private Long generalCharacteristicsId;
    @ManyToOne
    @JoinColumn(name = "metadata_status_id")
    private MetadataStatus metadataStatus;
    @Column(name = "metadata_status_id", insertable = false, updatable = false)
    private Long metadataStatusId;
}

