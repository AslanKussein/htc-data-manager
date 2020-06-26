package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KanbanService;
import kz.dilau.htcdatamanager.web.dto.ChangeStatusDto;
import kz.dilau.htcdatamanager.web.dto.CompleteDealDto;
import kz.dilau.htcdatamanager.web.dto.ConfirmCompleteDto;
import kz.dilau.htcdatamanager.web.dto.ForceCompleteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.KANBAN_REST_ENDPOINT)
public class KanbanResource {
    private final KanbanService kanbanService;


    @PostMapping("/changeStatus")
    public ResponseEntity<Long> changeStatus(@RequestBody ChangeStatusDto dto) {
        Long result = kanbanService.changeStatus(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/completeDeal")
    public ResponseEntity<Long> completeDeal(@RequestBody CompleteDealDto dto) {
        Long result = kanbanService.completeDeal(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/forceCompleteDeal")
    public ResponseEntity<Long> forceCompleteDeal(@RequestBody ForceCompleteDto dto) {
        Long result = kanbanService.forceCompleteDeal(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirmComplete")
    public ResponseEntity<Long> confirmComplete(@RequestBody ConfirmCompleteDto dto) {
        Long result = kanbanService.confirmComplete(dto);
        return ResponseEntity.ok(result);
    }
}
