package kz.dilau.htcdatamanager.domain;


import kz.dilau.htcdatamanager.domain.dictionary.PropertyDeveloper;
import lombok.*;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "general_characteristics")
public class GeneralCharacteristics extends AGeneralCharacteristics {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id")
    private PropertyDeveloper propertyDeveloper;
    @Column(name = "property_developer_id", insertable = false, updatable = false)
    private Long propertyDeveloperId;
    @Column(name = "housing_class")
    private String housingClass;
    @Column(name = "year_of_construction")
    private Integer yearOfConstruction;
    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
    @Column(name = "number_of_apartments")
    private Integer numberOfApartments;
    @Column(name = "apartments_on_the_site")
    private String apartmentsOnTheSite;
}
