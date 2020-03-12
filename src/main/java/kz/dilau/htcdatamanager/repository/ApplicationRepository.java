package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
