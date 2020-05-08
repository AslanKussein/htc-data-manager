package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.AddPhoneNumber;
import kz.dilau.htcdatamanager.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AddPhoneNumberRepository extends JpaRepository<AddPhoneNumber, Long> {
    Set<AddPhoneNumber> findByIdIn(List<Long> ids);
    Set<AddPhoneNumber> findByPhoneNumberIn(List<String> ids);
}
