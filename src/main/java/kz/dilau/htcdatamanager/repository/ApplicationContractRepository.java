package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.ApplicationContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationContractRepository extends JpaRepository<ApplicationContract, Long> {
    Optional<ApplicationContract> findByContractNumber(String contractNumber);
}
