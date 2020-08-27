package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;

import java.util.List;

public interface ResidentialComplexService {
    ResidentialComplexDto getById(Long id);

    List<ResidentialComplexDto> getAll();

    PageDto<ResidentialComplexDto> getAllPageable(PageableDto dto);

    ResidentialComplexDto save(String token, ResidentialComplexDto dto);

    ResidentialComplexDto update(String token, Long id, ResidentialComplexDto input);

    ResidentialComplexDto deleteById(String token, Long id);

    ResidentialComplexDto getByPostcode(String postcode);

    PageDto<ResidentialComplexDto> getByHouseName(String postcode);
}
