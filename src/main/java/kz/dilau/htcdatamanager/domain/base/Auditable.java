package kz.dilau.htcdatamanager.domain.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    protected U createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, updatable = false)
    protected U lastModifiedBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    protected Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date", nullable = false)
    protected Date lastModifiedDate;

    @Column(name = "is_removed", nullable = false, columnDefinition = "boolean default false")
    private Boolean isRemoved = false;
}
