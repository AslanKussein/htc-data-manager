package kz.dilau.htcdatamanager.web.rest.client;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationClientAutoCreateService;
import kz.dilau.htcdatamanager.service.ApplicationClientPayService;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientPayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_CLIENT_PAY)
public class ApplicationClientPayResource {
    private final ApplicationClientPayService applicationClientPayService;


    @PutMapping("/{id}")
    public ResponseEntity<ApplicationClientDTO> setPayed(@PathVariable("id") Long id,
                                       @RequestBody ApplicationClientPayDTO dto) {
        ApplicationClientDTO result = applicationClientPayService.update(id, dto);
        return ResponseEntity.ok(result);
    }

}
