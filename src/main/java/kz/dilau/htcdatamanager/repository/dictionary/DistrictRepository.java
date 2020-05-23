package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {
    List<District> findAllByParentIdAndIsRemovedFalse(Long parentId);

    Optional<District> findByKazPostId(String kazPostId);
}
