package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RealPropertyRepository extends JpaRepository<RealProperty, Long> {
    boolean existsByCadastralNumber(String cadastralNumber);

    RealProperty findByApartmentNumberAndBuildingId(String apartmentNumber, Long buildingId);

    @Query(value = "select p from RealProperty p " +
            "join Building b on p.buildingId = b.id " +
            "where p.apartmentNumber = :apartmentNumber and b.postcode = :postcode")
    RealProperty findByApartmentNumberAndPostcode(@Param("apartmentNumber") String apartmentNumber,
                                                  @Param("postcode") String postcode);
}
