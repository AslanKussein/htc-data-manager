package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = TABLE_NAME_PREFIX + "new_application_possible_reason_for_bidding",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "possible_reason_for_bidding_id")
    )
    private Set<PossibleReasonForBidding> possibleReasonsForBidding = new HashSet<>();
    @Column(name = "note")
    private String note;

    public Set<PossibleReasonForBidding> getPossibleReasonsForBidding() {
        if (isNull(possibleReasonsForBidding)) {
            possibleReasonsForBidding = new HashSet<>();
        }
        return possibleReasonsForBidding;
    }
}
