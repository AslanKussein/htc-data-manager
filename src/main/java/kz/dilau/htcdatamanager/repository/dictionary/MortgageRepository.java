package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.Mortgage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MortgageRepository extends JpaRepository<Mortgage, Long> {

    @Query(value = "select r from Mortgage r where r.isRemoved = false")
    List<Mortgage> findAllByRemovedFalse();

    @Query(value = "select r from Mortgage r where r.isRemoved = false")
    Page<Mortgage> findPageByRemovedFalse(Pageable pageable);
}
