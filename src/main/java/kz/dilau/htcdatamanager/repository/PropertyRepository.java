package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
