package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RealPropertyMetadataRepository extends JpaRepository<RealPropertyMetadata, Long> {
    @Query(value = "select m from RealPropertyMetadata m " +
            "where m.realProperty.id = :realPropertyId and m.application.id = :applicationId and m.metadataStatusId = :statusId")
    RealPropertyMetadata findByRealPropertyAndApplicationAndStatus(@Param("realPropertyId") Long realPropertyId,
                                                                   @Param("applicationId") Long applicationId,
                                                                   @Param("statusId") Long statusId);
}
