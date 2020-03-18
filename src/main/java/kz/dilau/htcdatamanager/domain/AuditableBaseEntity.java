package kz.dilau.htcdatamanager.domain;

import javax.persistence.*;

@MappedSuperclass
public class AuditableBaseEntity<T> extends Auditable<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
