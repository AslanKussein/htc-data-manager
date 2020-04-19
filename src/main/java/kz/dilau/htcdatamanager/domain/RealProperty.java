package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_type_id", referencedColumnName = "id", nullable = false)
    private ObjectType objectType;
    @Column(name = "cadastral_number", unique = true)
    private String cadastralNumber;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;
    @Column(name = "total_area", nullable = false)
    private Double totalArea;
    @Column(name = "living_area", nullable = false)
    private Double livingArea;
    @Column(name = "kitchen_area", nullable = false)
    private Double kitchenArea;
    @Column(name = "balcony_area")
    private Double balconyArea;
    @Column(name = "number_of_bedrooms", nullable = false)
    private Integer numberOfBedrooms;
    @Column(name = "atelier")
    private Boolean atelier;//студия
    @Column(name = "separate_bathroom")
    private Boolean separateBathroom;
    @OneToOne
    @MapsId
    private GeneralCharacteristics generalCharacteristics;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_complex_id", referencedColumnName = "id")
    private ResidentialComplex residentialComplex;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private PurchaseInfo purchaseInfo;

    @ElementCollection
    @CollectionTable(name = "real_property_files", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "file_id")
    private Set<String> filesIds;
}
