package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KazPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiModel(description = "Обработчик для апи казпочты")
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.APPLICATIONS_REST_ENDPOINT)
public class KazPostResource {

    KazPostService kazPostService;

    @PostMapping
    public ResponseEntity<Boolean> checkIsProcessingData(@RequestParam String jsonString) {
        return ResponseEntity.ok(kazPostService.processingData(jsonString));
    }
}
