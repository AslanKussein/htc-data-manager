package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealPropertyOwnerRepository extends JpaRepository<RealPropertyOwner, Long> {
    Optional<RealPropertyOwner> findByPhoneNumber(String phoneNumber);
}
