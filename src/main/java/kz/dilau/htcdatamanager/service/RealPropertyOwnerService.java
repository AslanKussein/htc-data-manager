package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.RealPropertyOwnerDto;

public interface RealPropertyOwnerService extends CommonService<Long, RealPropertyOwnerDto, RealPropertyOwnerDto> {
    RealPropertyOwnerDto findOwnerByPhoneNumber(String phoneNumber);
}
