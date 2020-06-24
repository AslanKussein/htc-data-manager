package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.NotesDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesService {

    NotesDto createNote(NotesDto dto);

    Page<NotesDto> getAllByRealPropertyId(Long id, Pageable pageable);

    NotesDto updateNote(NotesDto notesDto);

    NotesDto deleteNote(NotesDto notesDto);

    Integer getCountByRealPropertyId(Long id);

}
