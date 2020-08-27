package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResidentialComplexRepository extends PagingAndSortingRepository<ResidentialComplex, Long> {
    @Query(value = "select r from ResidentialComplex r where r.isRemoved = false")
    List<ResidentialComplex> findAllByRemovedFalse();

    @Query(value = "select r from ResidentialComplex r where r.isRemoved = false")
    Page<ResidentialComplex> findPageByRemovedFalse(Pageable pageable);

    List<ResidentialComplex> findByHouseNameContainingIgnoreCaseAndIsRemovedFalse(String houseName);




}
