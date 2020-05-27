package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ApplicationFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ApplicationFlagRepository extends JpaRepository<ApplicationFlag, Long> {
    Set<ApplicationFlag> findByIdIn(List<Long> ids);

    List<ApplicationFlag> findAllByParentIdAndIsRemovedFalse(Long parentId);
}
