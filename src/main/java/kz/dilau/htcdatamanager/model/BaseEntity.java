package kz.dilau.htcdatamanager.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {
    private T id;

    @Id
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
