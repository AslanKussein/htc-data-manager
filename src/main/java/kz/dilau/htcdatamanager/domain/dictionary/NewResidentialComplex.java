package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.Building;
import kz.dilau.htcdatamanager.domain.NewGeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "residential_complex")
public class NewResidentialComplex extends BaseEntity<Long> {
    @Column(name = "house_name")
    private String houseName;
    @Column(name = "number_of_entrances")
    private Integer numberOfEntrances;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "general_characteristics_id")
    private NewGeneralCharacteristics generalCharacteristics;
    @Column(name = "is_removed", nullable = false, columnDefinition = "boolean default false")
    private Boolean isRemoved = false;
}
