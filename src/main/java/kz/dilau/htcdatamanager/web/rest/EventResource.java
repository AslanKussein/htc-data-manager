package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.EVENTS_REST_ENDPOINT)
public class EventResource {
    private final EventService eventService;

    @PostMapping
    ResponseEntity<Long> addEvent(@RequestHeader(AUTHORIZATION) final String token,
                                  @RequestBody EventDto event) {
        Long id = eventService.addEvent(token, event);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@RequestHeader(AUTHORIZATION) final String token,
                                                 @PathVariable Long id) {
        try {
            EventDto event = eventService.getEventById(token, id);
            return ResponseEntity.ok(event);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Event with id %s not found", id, e)
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@RequestHeader(AUTHORIZATION) final String token,
                                            @PathVariable Long id,
                                            @RequestBody EventDto event) {
        try {
            eventService.updateEvent(token, id, event);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Provide correct event id: %s", id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@RequestHeader(AUTHORIZATION) final String token,
                                                @PathVariable Long id) {
        try {
            eventService.deleteEventById(token, id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Event with id %s not found", id)
            );
        }
    }
}
