package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.RealPropertyOwnerDto;

public interface RealPropertyOwnerManager extends CommonManager<Long, RealPropertyOwnerDto, RealPropertyOwnerDto> {
    RealPropertyOwnerDto findOwnerByPhoneNumber(String phoneNumber);
}
