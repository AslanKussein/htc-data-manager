package kz.dilau.htcdatamanager.web.rest.client;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationClientAutoCreateService;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_CLIENT_AUTO_CREATE_REST_ENDPOINT)
public class ApplicationClientAutoCreateResource {
    private final ApplicationClientAutoCreateService applicationClientAutoCreateService;


    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ApplicationDto dto) {
        Long result = applicationClientAutoCreateService.save(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id,
                                       @RequestBody ApplicationDto dto) {
        Long result = applicationClientAutoCreateService.update(id, dto);
        return ResponseEntity.ok(result);
    }

}
