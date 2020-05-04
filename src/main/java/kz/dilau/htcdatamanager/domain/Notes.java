package kz.dilau.htcdatamanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import lombok.*;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME_PREFIX + "notes")
public class Notes extends AuditableBaseEntity<String, Long> {

    private String text;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
