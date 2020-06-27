package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiOperation;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KanbanService;
import kz.dilau.htcdatamanager.web.dto.ChangeStatusDto;
import kz.dilau.htcdatamanager.web.dto.CompleteDealDto;
import kz.dilau.htcdatamanager.web.dto.ConfirmDealDto;
import kz.dilau.htcdatamanager.web.dto.ForceCloseDealDto;
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

    @ApiOperation(value = "Смена статусов в воронке перемещением")
    @PostMapping("/changeStatus")
    public ResponseEntity<Long> changeStatus(@RequestBody ChangeStatusDto dto) {
        Long result = kanbanService.changeStatus(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Завершение сделки прикреплением договоров")
    @PostMapping("/completeDeal")
    public ResponseEntity<Long> completeDeal(@RequestBody CompleteDealDto dto) {
        Long result = kanbanService.completeDeal(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Подтверждение завершения сделки бухгалтером")
    @PostMapping("/confirmComplete")
    public ResponseEntity<Long> confirmComplete(@RequestBody ConfirmDealDto dto) {
        Long result = kanbanService.confirmComplete(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Принудительное закрытие заявки агентом")
    @PostMapping("/forceCloseDeal")
    public ResponseEntity<Long> forceCloseDeal(@RequestBody ForceCloseDealDto dto) {
        Long result = kanbanService.forceCloseDeal(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Подтверждение закрытия заявки РГ")
    @PostMapping("/confirmCloseDeal")
    public ResponseEntity<Long> confirmCloseDeal(@RequestBody ConfirmDealDto dto) {
        Long result = kanbanService.confirmCloseDeal(dto);
        return ResponseEntity.ok(result);
    }
}