package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
    List<ResidentialComplex> findAllByRemovedFalse();
}
