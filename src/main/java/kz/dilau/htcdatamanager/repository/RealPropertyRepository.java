package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealPropertyRepository extends JpaRepository<RealProperty, Long> {
    boolean existsByCadastralNumber(String cadastralNumber);

}
