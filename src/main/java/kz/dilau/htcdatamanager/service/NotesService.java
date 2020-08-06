package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.NotesDto;

import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesService {

    NotesDto createNote(String login, NotesDto dto);

    Page<NotesDto> getAllByRealPropertyId(Long id, PageableDto pageable);

    NotesDto updateNote(String login, Long id, NotesDto notesDto);

    NotesDto deleteNote(String login, Long id);

    Integer getCountByRealPropertyId(Long id);

    NotesDto getById(Long id);

}
