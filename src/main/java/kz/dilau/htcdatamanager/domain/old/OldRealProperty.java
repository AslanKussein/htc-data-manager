package kz.dilau.htcdatamanager.domain.old;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "old_real_property")
public class OldRealProperty extends AuditableBaseEntity<String, Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_type_id")
    private ObjectType objectType;
    @Column(name = "object_type_id", insertable = false, updatable = false)
    private Long objectTypeId;
    @Column(name = "cadastral_number")
    private String cadastralNumber;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;
    @Column(name = "total_area")
    private BigDecimal totalArea;
    @Column(name = "living_area")
    private BigDecimal livingArea;
    @Column(name = "kitchen_area")
    private BigDecimal kitchenArea;
    @Column(name = "balcony_area")
    private BigDecimal balconyArea;
    @Column(name = "number_of_bedrooms")
    private Integer numberOfBedrooms;
    @Column(name = "atelier")
    private Boolean atelier;//студия
    @Column(name = "separate_bathroom")
    private Boolean separateBathroom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_complex_id")
    private OldResidentialComplex residentialComplex;
    @Column(name = "residential_complex_id", insertable = false, updatable = false)
    private Long residentialComplexId;
    //    @Convert(converter = FilesMapConverter.class)
    @Type(type = "jsonb")
    @Column(name = "files_map", columnDefinition = "jsonb")
    private Map<RealPropertyFileType, Set<String>> filesMap = new HashMap<>();
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
    private OldGeneralCharacteristics generalCharacteristics;
    @Column(name = "general_characteristics_id", insertable = false, updatable = false)
    private Long generalCharacteristicsId;
    @OneToOne(mappedBy = "realProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private OldPurchaseInfo purchaseInfo;
    @JsonIgnore
    @OneToOne(mappedBy = "realProperty")
    private OldApplication application;
    @Column(name= "latitude")
    private Double latitude; //широта
    @Column(name = "longitude")
    private Double longitude; //долгота


    public Map<RealPropertyFileType, Set<String>> getFilesMap() {
        if (filesMap == null) {
            filesMap = new HashMap<>();
        }
        return filesMap;
    }

    @Transient
    public MultiLangText getAddress() {
        return DictionaryMappingTool.mapAddressToMultiLang(getGeneralCharacteristics(), apartmentNumber);
    }

    @Transient
    public OldGeneralCharacteristics getGeneralCharacteristics() {
        if (nonNull(residentialComplex)) {
            generalCharacteristics = residentialComplex.getGeneralCharacteristics();
        }
        return generalCharacteristics;
    }

    @Transient
    public District getDistrict() {
        if (nonNull(getGeneralCharacteristics())) {
            return getGeneralCharacteristics().getDistrict();
        }
        return null;
    }
}
