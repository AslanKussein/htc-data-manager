package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.web.dto.ApplicationContractInfoDto;
import kz.dilau.htcdatamanager.web.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.EVENTS_REST_ENDPOINT)
public class EventResource {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Long> addEvent(@RequestBody EventDto event) {
        Long result = eventService.addEvent(event);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/getContractsInfo/{applicationId}")
    public ResponseEntity<ApplicationContractInfoDto> getContractsInfo(@PathVariable("applicationId") Long applicationId) {
        ApplicationContractInfoDto info = eventService.getContractsInfo(applicationId);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEvent(@ApiIgnore @RequestHeader(AUTHORIZATION) final String token,
                                            @PathVariable Long id,
                                            @RequestBody EventDto event) {
        Long result = eventService.updateEvent(token, id, event);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<Long> commentEvent(@ApiIgnore @RequestHeader(AUTHORIZATION) final String token,
                                             @PathVariable Long id,
                                             @RequestBody String comment) {
        Long result = eventService.commentEvent(token, id, comment);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteEventById(@ApiIgnore @RequestHeader(AUTHORIZATION) final String token,
                                                @PathVariable Long id) {
        Long result = eventService.deleteEventById(token, id);
        return ResponseEntity.ok(result);
    }
}
