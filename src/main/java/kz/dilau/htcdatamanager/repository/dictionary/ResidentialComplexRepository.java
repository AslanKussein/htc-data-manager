package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
    @Query(value = "select r from ResidentialComplex r where r.isRemoved = false")
    List<ResidentialComplex> findAllByRemovedFalse();
}
