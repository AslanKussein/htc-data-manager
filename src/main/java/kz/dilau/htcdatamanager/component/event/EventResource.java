package kz.dilau.htcdatamanager.component.event;

import kz.dilau.htcdatamanager.component.common.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventResource {
    private final EventManager eventManager;

    @PostMapping
    ResponseEntity<Long> addEvent(@RequestHeader(AUTHORIZATION) final String token,
                                  @RequestBody EventDto event) {
        Long id = eventManager.addEvent(token, event);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@RequestHeader(AUTHORIZATION) final String token,
                                                 @PathVariable Long id) {
        try {
            EventDto event = eventManager.getEventById(token, id);
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
            eventManager.updateEvent(token, id, event);
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
            eventManager.deleteEventById(token, id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Event with id %s not found", id)
            );
        }
    }
}
