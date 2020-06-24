package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.NotesDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesService {

    NotesDto createNote(NotesDto dto);

    Page<NotesDto> getAllByRealPropertyId(Long id, Pageable pageable);

    NotesDto updateNote(Long id, NotesDto notesDto);

    NotesDto deleteNote(Long id);

    Integer getCountByRealPropertyId(Long id);

}
