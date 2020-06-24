package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Notes;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.NotesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.web.dto.NotesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepository notesRepository;
    private final RealPropertyRepository realPropertyRepository;

    @Override
    public NotesDto createNote(NotesDto notesDto) {

        Optional<RealProperty> realProperty = realPropertyRepository.findById(notesDto.getRealPropertyId());
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(notesDto.getRealPropertyId());
        }

        Notes notes = new Notes();
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
    public NotesDto updateNote(Long id, NotesDto notesDto) {
        if (!nonNull(id)) {
            throw BadRequestException.idMustNotBeNull();
        }
        Notes notes = getNotesById(id);
        notes.setText(notesDto.getText());
        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }

    private Notes getNotesById(Long id) {
        Optional<Notes> notesOptional = notesRepository.findByIdAndIsRemovedFalse(id);
        if (!notesOptional.isPresent()) {
            throw NotFoundException.createNotesById(id);
        }
        return notesOptional.get();
    }

    @Override
    public NotesDto deleteNote(Long id) {
        if (!nonNull(id)) {
            throw BadRequestException.idMustNotBeNull();
        }
        Notes notes = getNotesById(id);
        notes.setIsRemoved(Boolean.TRUE);
        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }

    @Override
    public Integer getCountByRealPropertyId(Long realPropertyId) {
        return notesRepository.countByRealProperty_IdAndIsRemovedFalse(realPropertyId);
    }
}
