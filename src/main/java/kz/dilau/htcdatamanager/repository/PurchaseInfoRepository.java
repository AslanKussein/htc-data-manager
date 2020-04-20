package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo, Long> {
}
