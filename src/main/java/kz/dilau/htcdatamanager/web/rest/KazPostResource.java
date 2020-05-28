package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KazPostService;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import kz.dilau.htcdatamanager.web.dto.KazPostReturnDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiModel(description = "Обработчик для апи казпочты")
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.POST_REST_ENDPOINT)
@Log
public class KazPostResource {

    private final KazPostService kazPostService;

    @PostMapping
    public ResponseEntity<KazPostReturnDTO> processingData(@RequestBody KazPostDTO dto) {
        return ResponseEntity.ok(kazPostService.processingData(dto));
    }

    @GetMapping("{postCode}")
    public ResponseEntity<String> getPostData(@PathVariable String postCode) {
        return ResponseEntity.ok(kazPostService.getPostData(postCode));
    }
}
