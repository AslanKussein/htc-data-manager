package kz.dilau.htcdatamanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    private Long realPropertyId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "real_property_id" , updatable = false, insertable = false)
    private RealProperty realProperty;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createDate;

}
