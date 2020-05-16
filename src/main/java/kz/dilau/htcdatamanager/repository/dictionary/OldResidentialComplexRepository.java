package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.OldResidentialComplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OldResidentialComplexRepository extends PagingAndSortingRepository<OldResidentialComplex, Long> {
    @Query(value = "select r from OldResidentialComplex r where r.isRemoved = false")
    List<OldResidentialComplex> findAllByRemovedFalse();

    @Query(value = "select r from OldResidentialComplex r where r.isRemoved = false")
    Page<OldResidentialComplex> findPageByRemovedFalse(Pageable pageable);
}
