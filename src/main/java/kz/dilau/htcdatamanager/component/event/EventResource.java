package kz.dilau.htcdatamanager.component.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        EventDto event = eventManager.getEventById(token, id);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@RequestHeader(AUTHORIZATION) final String token,
                                            @PathVariable Long id,
                                            @RequestBody EventDto event) {
        eventManager.updateEvent(token, id, event);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@RequestHeader(AUTHORIZATION) final String token,
                                                @PathVariable Long id) {
        eventManager.deleteEventById(token, id);
        return ResponseEntity.noContent().build();
    }
}
