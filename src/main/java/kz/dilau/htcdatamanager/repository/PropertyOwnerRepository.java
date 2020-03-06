package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {
}
