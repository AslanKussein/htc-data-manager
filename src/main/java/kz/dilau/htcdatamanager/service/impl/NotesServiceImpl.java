package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Notes;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.NotesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
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
    private final RealPropertyRepository realPropertyRepository;

    @Override
    public NotesDto createNote(String login, NotesDto notesDto) {

        Optional<RealProperty> realProperty = realPropertyRepository.findById(notesDto.getRealPropertyId());
        if (!realProperty.isPresent()) {
            throw BadRequestException.findRealPropertyById(notesDto.getRealPropertyId());
        }

        Notes notes = new Notes();
        notes.setLogin(login);
        notes.setText(notesDto.getText());
        notes.setRealProperty(realProperty.get());

        notes = notesRepository.save(notes);

        return new NotesDto(notes);
    }

    @Override
    public Page<NotesDto> getAllByRealPropertyId(Long realPropertyId, Pageable pageable) {
        return notesRepository.findAllByRealProperty_IdAndDeletedFalse(realPropertyId, pageable)
                .map(NotesDto::new);
    }
}
