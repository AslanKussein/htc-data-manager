package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AApplicationData extends BaseEntity<Long> {
    @Column(name = "mortgage")
    private Boolean mortgage;//ипотека
    @Column(name = "has_probability_of_bidding")
    private Boolean probabilityOfBidding;//вероятность торга
    @Column(name = "the_size_of_trades")
    private Integer theSizeOfTrades;//размер торга
    @Column(name = "note")
    private String note;
}
