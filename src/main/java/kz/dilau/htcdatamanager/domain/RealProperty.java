package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property")
public class RealProperty extends AuditableBaseEntity<String, Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_type_id", referencedColumnName = "id")
    private ObjectType objectType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id", referencedColumnName = "id")
    private Street street;




    @Column(name = "cadastral_number", unique = true)
    private String cadastralNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_complex_id", referencedColumnName = "id")
    private ResidentialComplex residentialComplex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = TABLE_NAME_PREFIX + "real_property_info",
            joinColumns =
            @JoinColumn(name = "property_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "info_id", referencedColumnName = "id"))//todo rename table later)
    private GeneralCharacteristics generalCharacteristics;

    @Column(name = "floor")
    private Integer floor;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;
    @Column(name = "total_area")
    private Integer totalArea;
    @Column(name = "living_area")
    private Integer livingArea;
    @Column(name = "kitchen_area")
    private Integer kitchenArea;
    @Column(name = "balcony_area")
    private Integer balconyArea;
    @Column(name = "number_of_bedrooms")
    private Integer numberOfBedrooms;
    @Column(name = "atelier")
    private Boolean atelier;//студия
    @Column(name = "separate_bathroom")
    private Boolean separateBathroom;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private PurchaseInfo purchaseInfo;


    @ElementCollection
    @CollectionTable(name = "real_property_files", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "file_id")
    private Set<String> filesIds;
}
