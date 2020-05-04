package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiParam;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.web.dto.NotesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.data.domain.Pageable;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.NOTES_REST_ENDPOINT)
public class NotesResource {
    private final NotesService notesService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<NotesDto>> getAllByRealPropertyId(@PathVariable("id") Long id,
                                                                 @ApiParam Pageable pageable) {
        Page<NotesDto> dto = notesService.getAllByRealPropertyId(id, pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<NotesDto> createNote(@ApiIgnore @AuthenticationPrincipal final Principal principal,
                                               @RequestBody NotesDto notesDto) {
        NotesDto result = notesService.createNote(principal.getName(), notesDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<NotesDto> updateNote(@RequestBody NotesDto notesDto) {
        NotesDto result = notesService.updateNote(notesDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<NotesDto> deleteNote(@RequestBody NotesDto notesDto) {
        NotesDto result = notesService.deleteNote(notesDto);
        return ResponseEntity.ok(result);
    }
}
