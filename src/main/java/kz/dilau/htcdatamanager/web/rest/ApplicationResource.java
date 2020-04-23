package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.CommonResource;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_REST_ENDPOINT)
public class ApplicationResource implements CommonResource<Long, ApplicationDto, ApplicationDto> {
    private final ApplicationService applicationManager;

    @Override
    public ResponseEntity<ApplicationDto> getById(String token, Long aLong) {
        ApplicationDto dto = applicationManager.getById(token, aLong);
        return ResponseEntity.ok(dto);
    }

    @ApiIgnore
    @Override
    public ResponseEntity<List<ApplicationDto>> getAll(String token) {
        return null;
    }

    @Override
    public ResponseEntity<Long> save(String token, ApplicationDto input) {
        Long id = applicationManager.save(token, input);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<?> update(String token, Long aLong, ApplicationDto input) {
        applicationManager.update(token, aLong, input);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long aLong) {
        applicationManager.deleteById(token, aLong);
        return ResponseEntity.noContent().build();
    }
}
