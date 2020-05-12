package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PossibleReasonForBiddingRepository extends JpaRepository<PossibleReasonForBidding, Long> {
    Set<PossibleReasonForBidding> findByIdIn(List<Long> ids);

    List<PossibleReasonForBidding> findAllByParentIdAndIsRemovedFalse(Long parentId);
}
