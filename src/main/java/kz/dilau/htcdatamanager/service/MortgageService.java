package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;

import java.util.List;


public interface MortgageService {

    MortgageDto getById(Long id);

    List<MortgageDto> getAll();

    PageDto<MortgageDto> getAllPageable(PageableDto dto);

    MortgageDto save(String token, MortgageDto dto);

    MortgageDto update(String token, Long id, MortgageDto input);

    MortgageDto deleteById(String token, Long id);

}
