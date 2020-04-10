package kz.dilau.htcdatamanager.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseSystemDictionary extends BaseDictionary {
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true;
    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
