package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
