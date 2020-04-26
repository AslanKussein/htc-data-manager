package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;

import java.util.List;
import java.util.Set;

public interface PossibleReasonForBiddingRepository extends DictionaryRepository<PossibleReasonForBidding> {
    Set<PossibleReasonForBidding> findByIdIn(List<Long> ids);
}
