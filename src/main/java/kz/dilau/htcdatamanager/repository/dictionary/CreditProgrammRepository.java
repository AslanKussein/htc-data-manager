package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.CreditProgramm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditProgrammRepository extends JpaRepository<CreditProgramm, Long> {

    @Query(value = "select r from CreditProgramm r where r.isRemoved = false")
    List<CreditProgramm> findAllByRemovedFalse();

    @Query(value = "select r from CreditProgramm r where r.isRemoved = false")
    Page<CreditProgramm> findPageByRemovedFalse(Pageable pageable);
}
