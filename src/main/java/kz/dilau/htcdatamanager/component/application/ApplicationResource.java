package kz.dilau.htcdatamanager.component.application;

import kz.dilau.htcdatamanager.module.CommonResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationResource implements CommonResource<Long, ApplicationDto, ApplicationDto> {
    private final ApplicationManager applicationManager;

    @Override
    public ResponseEntity<ApplicationDto> getById(String token, Long aLong) {
        return null;
    }

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
        return null;
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long aLong) {
        return null;
    }
}
