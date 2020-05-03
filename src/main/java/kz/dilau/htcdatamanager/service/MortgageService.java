package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.MortgageDto;


public interface MortgageService {

    MortgageDto save(String token, MortgageDto dto);


}
