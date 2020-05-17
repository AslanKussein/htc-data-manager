package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldRealProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OldRealPropertyRepository extends JpaRepository<OldRealProperty, Long> {
    boolean existsByCadastralNumber(String cadastralNumber);
}
