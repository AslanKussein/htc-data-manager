package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.ApplicationSellData;
import kz.dilau.htcdatamanager.domain.Notes;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.NotesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.service.NotificationService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.NotesDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepository notesRepository;
    private final RealPropertyRepository realPropertyRepository;

    private final NotificationService notificationService;

    @Override
    public NotesDto createNote(String login, NotesDto notesDto) {

        Optional<RealProperty> realProperty = realPropertyRepository.findById(notesDto.getRealPropertyId());
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(notesDto.getRealPropertyId());
        }

        Notes notes = new Notes();
        notes.setText(notesDto.getText());
        notes.setRealProperty(realProperty.get());

        if (nonNull(notesDto.getQuestionId())) {
            Notes question = getNotesById(notesDto.getQuestionId());
            notes.setQuestionId(question.getId());
        }

        notes = notesRepository.save(notes);

        if (nonNull(notes.getRealProperty())) {
            for (ApplicationSellData sellData : notes.getRealProperty().getSellDataList()) {
                if (nonNull(notesDto.getQuestionId())) {
                    notificationService.createNotesAnswerNotification(sellData.getApplication().getId(), notes.getId());
                } else {
                    notificationService.createNotesNotification(sellData.getApplication().getId(), notes.getId());
                }
            }
        }
        return new NotesDto(notes);
    }

    @Override
    public Page<NotesDto> getAllByRealPropertyId(Long realPropertyId, PageableDto pageable) {
        return notesRepository.findAllByRealProperty_IdAndIsRemovedFalse(realPropertyId, PageableUtils.createPageRequest(pageable))
                .map(NotesDto::new);
    }

    @Override
    public NotesDto updateNote(String login, Long id, NotesDto notesDto) {
        if (isNull(id)) {
            throw BadRequestException.idMustNotBeNull();
        }


        Notes notes = getNotesById(id);
        notes.setText(notesDto.getText());

        if (nonNull(notesDto.getQuestionId())) {
            Notes question = getNotesById(notesDto.getQuestionId());
            notes.setQuestionId(question.getId());
        }

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
    public NotesDto deleteNote(String login, Long id) {
        if (isNull(id)) {
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
