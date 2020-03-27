package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<Info, Long> {
}
