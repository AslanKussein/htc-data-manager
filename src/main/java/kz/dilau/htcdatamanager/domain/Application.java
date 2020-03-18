package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "htc_dm_application")
public class Application extends AuditableBaseEntity<Application> {
    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    private OperationType operationType;
    @ManyToOne
    @JoinColumn(name = "object_type_id", referencedColumnName = "id")
    private ObjectType objectType;
    private Double objectPrice;
    private Boolean ipoteka;
    private Boolean obremenenie;
    private Boolean obmen;
    private Boolean obshayaDolevayaSobstvennost;
    private Boolean hasTorg;
    private Integer razmerTorga;
    private String torgReason;
    private Date srokDogovora;
    private Integer sum;
    private boolean isWithCommission;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Double getObjectPrice() {
        return objectPrice;
    }

    public void setObjectPrice(Double objectPrice) {
        this.objectPrice = objectPrice;
    }

    public Boolean getIpoteka() {
        return ipoteka;
    }

    public void setIpoteka(Boolean ipoteka) {
        this.ipoteka = ipoteka;
    }

    public Boolean getObremenenie() {
        return obremenenie;
    }

    public void setObremenenie(Boolean obremenenie) {
        this.obremenenie = obremenenie;
    }

    public Boolean getObmen() {
        return obmen;
    }

    public void setObmen(Boolean obmen) {
        this.obmen = obmen;
    }

    public Boolean getObshayaDolevayaSobstvennost() {
        return obshayaDolevayaSobstvennost;
    }

    public void setObshayaDolevayaSobstvennost(Boolean obshayaDolevayaSobstvennost) {
        this.obshayaDolevayaSobstvennost = obshayaDolevayaSobstvennost;
    }

    public Boolean getHasTorg() {
        return hasTorg;
    }

    public void setHasTorg(Boolean hasTorg) {
        this.hasTorg = hasTorg;
    }

    public Integer getRazmerTorga() {
        return razmerTorga;
    }

    public void setRazmerTorga(Integer razmerTorga) {
        this.razmerTorga = razmerTorga;
    }

    public String getTorgReason() {
        return torgReason;
    }

    public void setTorgReason(String torgReason) {
        this.torgReason = torgReason;
    }

    public Date getSrokDogovora() {
        return srokDogovora;
    }

    public void setSrokDogovora(Date srokDogovora) {
        this.srokDogovora = srokDogovora;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public boolean isWithCommission() {
        return isWithCommission;
    }

    public void setWithCommission(boolean withCommission) {
        isWithCommission = withCommission;
    }
}
