package kz.dilau.htcdatamanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "favorites")
public class Favorites extends BaseEntity<Long> {

    private String clientLogin;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;

    @Column(name = "real_property_id", updatable = false, insertable = false)
    private Long realPropertyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createDate;

}
