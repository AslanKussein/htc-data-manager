package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.domain.dictionary.YardType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AGeneralCharacteristics extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_of_construction_id")
    private MaterialOfConstruction materialOfConstruction;
    @Column(name = "material_of_construction_id", insertable = false, updatable = false)
    private Long materialOfConstructionId;
    @Column(name = "concierge")
    private Boolean concierge;
    @Column(name = "wheelchair")
    private Boolean wheelchair;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_type_id")
    private YardType yardType;
    @Column(name = "yard_type_id", insertable = false, updatable = false)
    private Long yardTypeId;
    @Column(name = "playground")
    private Boolean playground;
}
