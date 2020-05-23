package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "real_property_file")
public class RealPropertyFile extends AuditableBaseEntity<String, Long> {
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Type(type = "jsonb")
    @Column(name = "files_map", columnDefinition = "jsonb")
    private Map<RealPropertyFileType, Set<String>> filesMap = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "metadata_status_id")
    private MetadataStatus metadataStatus;
    @Column(name = "metadata_status_id", insertable = false, updatable = false)
    private Long metadataStatusId;

    public Map<RealPropertyFileType, Set<String>> getFilesMap() {
        if (filesMap == null) {
            filesMap = new HashMap<>();
        }
        return filesMap;
    }

    public RealPropertyFile(RealPropertyDto dataDto) {
        if (!CollectionUtils.isEmpty(dataDto.getHousingPlanImageIdList())) {
            getFilesMap().put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(dataDto.getHousingPlanImageIdList()));
        }
        if (!CollectionUtils.isEmpty(dataDto.getPhotoIdList())) {
            getFilesMap().put(RealPropertyFileType.PHOTO, new HashSet<>(dataDto.getPhotoIdList()));
        }
        if (!CollectionUtils.isEmpty(dataDto.getVirtualTourImageIdList())) {
            getFilesMap().put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(dataDto.getVirtualTourImageIdList()));
        }
    }
}