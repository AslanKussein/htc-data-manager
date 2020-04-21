package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.HeatingSystem;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.domain.dictionary.Sewerage;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property")
public class RealProperty extends AuditableBaseEntity<String, Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_type_id")
    private ObjectType objectType;
    @Column(name = "cadastral_number", unique = true)
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
    private ResidentialComplex residentialComplex;
    //    @Convert(converter = FilesMapConverter.class)
    @Type(type = "jsonb")
    @Column(name = "files_map", columnDefinition = "jsonb")
    private Map<RealPropertyFileType, Set<String>> filesMap = new HashMap<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sewerage_id")
    private Sewerage sewerage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heating_system_id")
    private HeatingSystem heatingSystem;
    @Column(name = "land_area")
    private BigDecimal landArea;
    @OneToOne
    @JoinColumn(name = "general_characteristics_id")
    private GeneralCharacteristics generalCharacteristics;

    public Map<RealPropertyFileType, Set<String>> getFilesMap() {
        if (filesMap == null) {
            filesMap = new HashMap<>();
        }
        return filesMap;
    }
}
