package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealPropertyAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<RealPropertyAnalytics, Long> {
    RealPropertyAnalytics findByBuildingId(Long buildingId);

    RealPropertyAnalytics findByDistrictIdAndHouseClassId(Long districtId, Long houseClassId);
}
