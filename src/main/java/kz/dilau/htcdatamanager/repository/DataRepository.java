package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldPurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<OldPurchaseInfo, Long> {
}
