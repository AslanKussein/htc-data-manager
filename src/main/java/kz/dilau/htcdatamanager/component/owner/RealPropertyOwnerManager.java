package kz.dilau.htcdatamanager.component.owner;

import kz.dilau.htcdatamanager.component.common.CommonManager;

public interface RealPropertyOwnerManager extends CommonManager<Long, RealPropertyOwnerDto, RealPropertyOwnerDto> {
    RealPropertyOwnerDto findOwnerByPhoneNumber(String phoneNumber);
}