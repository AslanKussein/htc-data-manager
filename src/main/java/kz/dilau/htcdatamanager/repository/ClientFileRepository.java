package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.ClientFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientFileRepository extends JpaRepository<ClientFile, Long> {
}
