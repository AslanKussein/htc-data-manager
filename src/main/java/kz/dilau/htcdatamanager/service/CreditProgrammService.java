package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.CreditProgrammDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;

import java.util.List;


public interface CreditProgrammService {

    CreditProgrammDto getById(Long id);

    List<CreditProgrammDto> getAll();

    CreditProgrammDto save(String token, CreditProgrammDto dto);

    CreditProgrammDto update(String token, Long id, CreditProgrammDto input);

    CreditProgrammDto deleteById(String token, Long id);

}
