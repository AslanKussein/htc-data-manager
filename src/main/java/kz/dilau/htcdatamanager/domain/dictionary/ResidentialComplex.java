package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.AuditableBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "residential_complex")
public class ResidentialComplex extends AuditableBaseEntity<String, Long> {
//    @NotNull(message = "Country must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;


    @Column(name = "house_name")
    private String houseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id", referencedColumnName = "id")
    private PropertyDeveloper propertyDeveloper;

//    @Min(1)
    @Column(name = "number_of_entrances")
    private Integer numberOfEntrances;
//    @Min(1)
    @Column(name = "number_of_apartments")
    private Integer numberOfApartments;

    @Column(name = "housing_class")
    private String housingClass;
    @Column(name = "housing_condition")
    private String housingCondition;


}
