package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientDto;
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
@Table(name = TABLE_NAME_PREFIX + "real_property_metadata")
public class RealPropertyMetadata extends AuditableBaseEntity<String, Long> {
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

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
    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "residential_complex_id")
//    private ResidentialComplex residentialComplex;
//    @Column(name = "residential_complex_id", insertable = false, updatable = false)
//    private Long residentialComplexId;
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
    private GeneralCharacteristics generalCharacteristics;
    @Column(name = "general_characteristics_id", insertable = false, updatable = false)
    private Long generalCharacteristicsId;
    @ManyToOne
    @JoinColumn(name = "metadata_status_id")
    private MetadataStatus metadataStatus;
    @Column(name = "metadata_status_id", insertable = false, updatable = false)
    private Long metadataStatusId;

    public RealPropertyMetadata(RealPropertyDto dto, Sewerage sewerage,
                                HeatingSystem heatingSystem,
                                PropertyDeveloper propertyDeveloper, HouseCondition houseCondition,
                                MaterialOfConstruction materialOfConstruction, YardType yardType) {
        this.floor = dto.getFloor();
        this.numberOfRooms = dto.getNumberOfRooms();
        this.numberOfBedrooms = dto.getNumberOfBedrooms();
        this.totalArea = dto.getTotalArea();
        this.livingArea = dto.getLivingArea();
        this.kitchenArea = dto.getKitchenArea();
        this.balconyArea = dto.getBalconyArea();
        this.atelier = dto.getAtelier();
        this.separateBathroom = dto.getSeparateBathroom();
        this.sewerage = sewerage;
        this.heatingSystem = heatingSystem;
        this.landArea = dto.getLandArea();
        this.generalCharacteristics = new GeneralCharacteristics(dto.getGeneralCharacteristicsDto(),
                propertyDeveloper, houseCondition, materialOfConstruction, yardType);
    }

    public RealPropertyMetadata(RealPropertyClientDto dto) {
        this.floor = dto.getFloor();
        this.numberOfRooms = dto.getNumberOfRooms();
        this.totalArea = dto.getTotalArea();
        this.livingArea = dto.getLivingArea();
        this.atelier = dto.getAtelier();
        this.separateBathroom = dto.getSeparateBathroom();
    }
}

