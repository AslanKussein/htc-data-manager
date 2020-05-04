package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Long> {

    Page<Notes> findAllByRealProperty_IdAndIsRemovedFalse(Long id, Pageable pageable);

    Optional<Notes> findByIdAndIsRemovedFalse(Long id);
}
