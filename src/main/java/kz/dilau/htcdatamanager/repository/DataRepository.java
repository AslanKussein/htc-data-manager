package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<PurchaseInfo, Long> {
}
