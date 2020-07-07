package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiOperation;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KanbanService;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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

    @ApiOperation(value = "Получение данных по заявке для завершения сделки")
    @PostMapping("/applicationInfo/{id}")
    public ResponseEntity<CompleteApplicationDto> applicationInfo(@PathVariable("id") Long applicationId) {
        CompleteApplicationDto result = kanbanService.applicationInfo(applicationId);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Получение данных по связанной заявке для завершения сделки")
    @PostMapping("/targetApplicationInfo/{id}")
    public ResponseEntity<CompleteTargetApplicationDto> targetApplicationInfo(@PathVariable("id") Long applicationId) {
        CompleteTargetApplicationDto result = kanbanService.targetApplicationInfo(applicationId);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Завершение сделки прикреплением договоров")
    @PostMapping("/completeDeal")
    public ResponseEntity<Long> completeDeal(@RequestBody CompleteDealDto dto) {
        Long result = kanbanService.completeDeal(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Подтверждение успешного завершения сделки бухгалтером")
    @PostMapping("/confirmComplete")
    public ResponseEntity<Long> confirmComplete(@RequestBody ConfirmDealDto dto) {
        Long result = kanbanService.confirmComplete(dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Принудительное закрытие заявки агентом")
    @PostMapping("/forceCloseDeal")
    public ResponseEntity<Long> forceCloseDeal(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                               @RequestBody ForceCloseDealDto dto) {
        Long result = kanbanService.forceCloseDeal(token, dto);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Подтверждение закрытия заявки РГ")
    @PostMapping("/confirmCloseDeal")
    public ResponseEntity<Long> confirmCloseDeal(@RequestBody ConfirmDealDto dto) {
        Long result = kanbanService.confirmCloseDeal(dto);
        return ResponseEntity.ok(result);
    }
}
