package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Long> {
}
