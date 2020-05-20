package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property")
public class RealProperty extends AuditableBaseEntity<String, Long> {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building;
    @Column(name = "building_id", insertable = false, updatable = false)
    private Long buildingId;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "cadastral_number")
    private String cadastralNumber;

    @OneToMany(mappedBy = "realProperty")
    private List<RealPropertyMetadata> metadataList;

    public RealProperty(RealPropertyDto realPropertyDto, Building building, RealPropertyMetadata metadata) {
        this.id = realPropertyDto.getId();
        this.building = building;
        this.apartmentNumber = realPropertyDto.getApartmentNumber();
        this.cadastralNumber = realPropertyDto.getCadastralNumber();
        getMetadataList().add(metadata);
    }

    public List<RealPropertyMetadata> getMetadataList() {
        if (isNull(metadataList)) {
            metadataList = new ArrayList<>();
        }
        return metadataList;
    }

    @Transient
    public RealPropertyMetadata getMetadataByStatus(Long statusId) {
        return getMetadataList().stream().filter(data -> data.getMetadataStatusId().equals(statusId)).findFirst().orElse(null);
    }
}
