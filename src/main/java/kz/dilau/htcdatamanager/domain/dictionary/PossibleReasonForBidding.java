package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "possible_reason_for_bidding")
public class PossibleReasonForBidding extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "operation_type_id")
    private OperationType operationType;

    @Column(name = "operation_type_id", insertable = false, updatable = false)
    private Long parentId;

    @Column(name = "operation_code")
    private String operationCode;
}
