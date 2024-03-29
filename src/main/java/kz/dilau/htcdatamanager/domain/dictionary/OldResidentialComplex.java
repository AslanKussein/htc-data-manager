package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.old.OldGeneralCharacteristics;
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
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "old_residential_complex")
public class OldResidentialComplex extends BaseEntity<Long> {
    @Column(name = "house_name")
    private String houseName;
    @Column(name = "number_of_entrances")
    private Integer numberOfEntrances;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "general_characteristics_id")
    private OldGeneralCharacteristics generalCharacteristics;
    @Column(name = "is_removed", nullable = false, columnDefinition = "boolean default false")
    private Boolean isRemoved = false;
}
