package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.NotesDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesService {

    NotesDto createNote(String name, NotesDto dto);

    Page<NotesDto> getAllByRealPropertyId(Long id, Pageable pageable);

    NotesDto updateNote(String name, Long id, NotesDto notesDto);

    NotesDto deleteNote(String name, Long id);

    Integer getCountByRealPropertyId(Long id);

}
