package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
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

    @OneToMany(mappedBy = "realProperty", cascade = CascadeType.ALL)
    private List<RealPropertyMetadata> metadataList;

    @OneToMany(mappedBy = "realProperty", cascade = CascadeType.ALL)
    private List<RealPropertyFile> fileList;

    @OneToMany(mappedBy = "realProperty", fetch = FetchType.LAZY)
    private List<ApplicationSellData> sellDataList;

    public RealProperty(RealPropertyDto realPropertyDto, Building building, RealPropertyMetadata metadata) {
        this.building = building;
        this.apartmentNumber = realPropertyDto.getApartmentNumber();
        this.cadastralNumber = realPropertyDto.getCadastralNumber();
        metadata.setRealProperty(this);
        getMetadataList().add(metadata);
    }

    public List<RealPropertyMetadata> getMetadataList() {
        if (isNull(metadataList)) {
            metadataList = new ArrayList<>();
        }
        return metadataList;
    }

    public List<RealPropertyFile> getFileList() {
        if (isNull(fileList)) {
            fileList = new ArrayList<>();
        }
        return fileList;
    }

    public List<ApplicationSellData> getSellDataList() {
        if (isNull(sellDataList)) {
            sellDataList = new ArrayList<>();
        }
        return sellDataList;
    }

    @Transient
    public List<ApplicationSellData> getActualSellDataList() {
        return getSellDataList().stream().filter(item -> !item.getApplication().getIsRemoved()).collect(Collectors.toList());
    }

    @Transient
    public RealPropertyMetadata getMetadataByStatus(Long statusId) {
        List<RealPropertyMetadata> metadataListByStatus = getMetadataListByStatus(statusId);
        if (nonNull(metadataListByStatus) && !metadataListByStatus.isEmpty()) {
            return metadataListByStatus.get(0);
        }
        return null;
    }

    @Transient
    public List<RealPropertyMetadata> getMetadataListByStatus(Long statusId) {
        return getMetadataList().stream().filter(data -> data.getMetadataStatusId().equals(statusId)).collect(Collectors.toList());
    }

    @Transient
    public List<RealPropertyFile> getFilesByStatus(Long statusId) {
        return getFileList().stream().filter(data -> data.getMetadataStatusId().equals(statusId)).collect(Collectors.toList());
    }

    @Transient
    public RealPropertyFile getFileByStatus(Long statusId) {
        List<RealPropertyFile> filesByStatus = getFilesByStatus(statusId);
        if (nonNull(filesByStatus) && !filesByStatus.isEmpty()) {
            return filesByStatus.get(0);
        }
        return null;
    }

    @Transient
    public RealPropertyMetadata getMetadataByStatusAndApplication(Long statusId, Long applicationId) {
        return getMetadataList()
                .stream()
                .filter(data -> data.getMetadataStatusId().equals(statusId) && nonNull(data.getApplication()) && data.getApplication().getId().equals(applicationId))
                .findFirst().orElse(null);
    }

    @Transient
    public RealPropertyFile getFileByStatusAndApplication(Long statusId, Long applicationId) {
        return getFileList()
                .stream()
                .filter(data -> data.getMetadataStatusId().equals(statusId) && nonNull(data.getApplication()) && data.getApplication().getId().equals(applicationId))
                .findFirst().orElse(null);
    }
}
