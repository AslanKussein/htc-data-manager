package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.HashMap;
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
@Table(name = TABLE_NAME_PREFIX + "sell_data")
public class ApplicationSellData extends AApplicationData {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "real_property_id")
    @ToString.Exclude
    private RealProperty realProperty;
    @Column(name = "object_price")
    private BigDecimal objectPrice;
    @Column(name = "encumbrance")
    private Boolean encumbrance;//обременение
    @Column(name = "is_shared_ownership_property")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @Column(name = "is_exchange")
    private Boolean exchange;//обмен
    @Type(type = "jsonb")
    @Column(name = "files_map", columnDefinition = "jsonb")
    private Map<RealPropertyFileType, Set<String>> filesMap = new HashMap<>();

    public Map<RealPropertyFileType, Set<String>> getFilesMap() {
        if (filesMap == null) {
            filesMap = new HashMap<>();
        }
        return filesMap;
    }

}
