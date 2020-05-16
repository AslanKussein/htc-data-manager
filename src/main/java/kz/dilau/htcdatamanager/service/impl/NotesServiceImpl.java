package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.old.OldNotes;
import kz.dilau.htcdatamanager.domain.old.OldRealProperty;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.NotesRepository;
import kz.dilau.htcdatamanager.repository.OldRealPropertyRepository;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.web.dto.NotesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepository notesRepository;
    private final OldRealPropertyRepository realPropertyRepository;

    @Override
    public NotesDto createNote(String login, NotesDto notesDto) {

        Optional<OldRealProperty> realProperty = realPropertyRepository.findById(notesDto.getRealPropertyId());
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(notesDto.getRealPropertyId());
        }

        OldNotes notes = new OldNotes();
        notes.setText(notesDto.getText());
        notes.setRealProperty(realProperty.get());

        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }

    @Override
    public Page<NotesDto> getAllByRealPropertyId(Long realPropertyId, Pageable pageable) {
        return notesRepository.findAllByRealProperty_IdAndIsRemovedFalse(realPropertyId, pageable)
                .map(NotesDto::new);
    }

    @Override
    public NotesDto updateNote(NotesDto notesDto) {
        if (notesDto.getId() == null) {
            throw BadRequestException.idMustNotBeNull();
        }
        OldNotes notes = getNotesById(notesDto.getId());
        notes.setText(notesDto.getText());
        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }

    private OldNotes getNotesById(Long id) {
        Optional<OldNotes> notesOptional = notesRepository.findByIdAndIsRemovedFalse(id);
        if (!notesOptional.isPresent()) {
            throw NotFoundException.createNotesById(id);
        }
        return notesOptional.get();
    }

    @Override
    public NotesDto deleteNote(NotesDto notesDto) {
        if (notesDto.getId() == null) {
            throw BadRequestException.idMustNotBeNull();
        }
        OldNotes notes = getNotesById(notesDto.getId());
        notes.setIsRemoved(Boolean.TRUE);
        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }
}
