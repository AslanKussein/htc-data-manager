package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.SettingsService;
import kz.dilau.htcdatamanager.web.dto.SettingsDto;
import kz.dilau.htcdatamanager.web.rest.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.SETTINGS_ENDPOINT)
public class SettingsResource {
    private final SettingsService settingsService;

    @GetMapping("/getValue/{key}")
    public ResponseEntity getValue(@PathVariable(value = "key") String pkey) {
        return ApiResponse.OK(settingsService.getSettings(pkey));
    }

    @PostMapping("/add")
    public ResponseEntity getValue(@RequestBody SettingsDto dto) {
        return ApiResponse.OK(settingsService.save(dto));
    }

    @GetMapping("/all")
    public ResponseEntity getValue() {
        return ApiResponse.OK(settingsService.all());
    }
}
