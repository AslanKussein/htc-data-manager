package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TypeOfElevatorRepository extends JpaRepository<TypeOfElevator, Long> {
    Set<TypeOfElevator> findByIdIn(List<Long> ids);
}
