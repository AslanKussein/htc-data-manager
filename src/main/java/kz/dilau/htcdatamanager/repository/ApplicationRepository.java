package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    @Query(value = "select a from Application a " +
            "join RealPropertyMetadata m on m.application.id = a.id " +
            "where a.isRemoved = false and m.metadataStatusId = :metadataStatusId")
    Page<Application> findAllByMetadataStatus(@Param("metadataStatusId") Long metadataStatusId,
                                              Pageable pageable);

    @Query(value = "select a from Application a " +
            "join RealPropertyFile f on f.application.id = a.id " +
            "where a.isRemoved = false and f.metadataStatusId = :metadataStatusId")
    Page<Application> findAllFileByMetadataStatus(@Param("metadataStatusId") Long metadataStatusId,
                                                  Pageable pageable);


    @Query(value = "select a from Application a " +
            "join ApplicationSellData f on f.application.id = a.id " +
            "join RealProperty p  on f.realProperty.id = p.id " +
            "join Building b on p.buildingId = b.id " +
             "where a.isRemoved = false and b.postcode = :postcode")
    List<Application> findAllByPostcode(@Param("postcode") String postcode);

    Optional<Application> findByIdAndIsRemovedFalse(Long id);

}
