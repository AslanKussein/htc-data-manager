package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;

import java.util.List;

public interface ResidentialComplexService {
    ResidentialComplexDto getById(Long id);

    List<ResidentialComplexDto> getAll();

    ResidentialComplexDto save(String token, ResidentialComplexDto dto);

    ResidentialComplexDto update(String token, Long id, ResidentialComplexDto input);

    ResidentialComplexDto deleteById(String token, Long id);
}
