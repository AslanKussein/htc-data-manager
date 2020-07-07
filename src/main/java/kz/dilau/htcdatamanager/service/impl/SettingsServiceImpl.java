package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Settings;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.SettingsRepository;
import kz.dilau.htcdatamanager.service.SettingsService;
import kz.dilau.htcdatamanager.web.dto.SettingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;

    @Override
    public SettingsDto getSettings(String pKey) {
        if (isNull(pKey)) throw BadRequestException.createRequiredIsEmpty("key");
        Settings settings = settingsRepository.findByKey(pKey).orElse(null);

        if (isNull(settings)) return null;

        return new SettingsDto(settings.getKey(), settings.getVal());
    }

    @Override
    public Long save(SettingsDto dto) {
        if (isNull(dto.getKey())) throw BadRequestException.createRequiredIsEmpty("key");
        Settings settings = settingsRepository.findByKey(dto.getKey()).orElse(null);

        if (nonNull(settings)) {
            settings.setVal(dto.getVal());
        } else {
            settings = new Settings(dto.getKey(), dto.getVal());
        }
        settingsRepository.saveAndFlush(settings);
        return settings.getId();
    }

    @Override
    public List<SettingsDto> all() {
        List<Settings> settingsList = settingsRepository.findAll();
        List<SettingsDto> sdto = new ArrayList<>();
        settingsList.forEach( e -> sdto.add(new SettingsDto(e.getKey(), e.getVal())));
        return sdto;
    }
}
