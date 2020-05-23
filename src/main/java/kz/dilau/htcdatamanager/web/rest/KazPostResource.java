package kz.dilau.htcdatamanager.web.rest;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.KazPostService;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiModel(description = "Обработчик для апи казпочты")
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.POST_REST_ENDPOINT)
public class KazPostResource {

    private final KazPostService kazPostService;

    @PostMapping
    public ResponseEntity<String> getPostData(@RequestParam(name = "jsonString") String jsonString) {
        KazPostDTO dto = new Gson().fromJson(jsonString, KazPostDTO.class);
        return ResponseEntity.ok(kazPostService.processingData(dto).getId());
    }
}
