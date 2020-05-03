package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.NotesDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesService {

    NotesDto createNote(String login, NotesDto dto);

    Page<NotesDto> getAllByRealPropertyId(Long id, Pageable pageable);
}
