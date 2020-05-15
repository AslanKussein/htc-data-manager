package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhoneNumber(String phoneNumber);

    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByPhoneNumberIgnoreCase(String phoneNumber);
}
