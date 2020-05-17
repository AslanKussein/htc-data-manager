package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.old.OldNotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<OldNotes, Long> {

    Page<OldNotes> findAllByRealProperty_IdAndIsRemovedFalse(Long id, Pageable pageable);

    Optional<OldNotes> findByIdAndIsRemovedFalse(Long id);
}
