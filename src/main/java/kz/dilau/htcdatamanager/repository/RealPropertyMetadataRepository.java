package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RealPropertyMetadataRepository extends PagingAndSortingRepository<RealPropertyMetadata, Long> {

    @Query(value = "select m from RealPropertyMetadata m " +
            "where m.realProperty.id = :realPropertyId and m.application.id = :applicationId and m.metadataStatusId = :statusId")
    RealPropertyMetadata findByRealPropertyAndApplicationAndStatus(@Param("realPropertyId") Long realPropertyId,
                                                                   @Param("applicationId") Long applicationId,
                                                                   @Param("statusId") Long statusId);

    @Query(value = "select m from RealPropertyMetadata m " +
            "where m.application.isRemoved = false and m.metadataStatusId = :metadataStatusId")
    Page<RealPropertyMetadata> findAllByMetadataStatus(@Param("metadataStatusId") Long metadataStatusId,
                                                       Pageable pageable);
}
