package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.ApplicationContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationContractRepository extends JpaRepository<ApplicationContract, Long> {
}
