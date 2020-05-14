package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.ClientPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ClientPhoneNumberRepository extends JpaRepository<ClientPhoneNumber, Long> {
    Set<ClientPhoneNumber> findByIdIn(List<Long> ids);
    Set<ClientPhoneNumber> findByPhoneNumberIn(List<String> ids);
    List<ClientPhoneNumber> getAllByClient_Id(Long id);
}
