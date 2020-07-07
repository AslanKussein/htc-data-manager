package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.SettingsDto;

import java.util.List;

public interface SettingsService {
    SettingsDto getSettings(String pKey);

    Long save(SettingsDto dto);

    List<SettingsDto> all();
}
