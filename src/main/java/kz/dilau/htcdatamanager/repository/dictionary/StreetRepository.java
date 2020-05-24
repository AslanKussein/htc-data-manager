package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Long>, JpaSpecificationExecutor<Street> {
    List<Street> findAllByParentIdAndIsRemovedFalse(Long parentId);

    Optional<Street> findByKazPostIdAndStreetType_Id(String kazPostId, String streetTypeId);
}
