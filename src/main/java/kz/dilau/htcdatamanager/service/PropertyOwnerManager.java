package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;

public interface PropertyOwnerManager {
    RealPropertyOwner getOwnerById(Long id);

    RealPropertyOwner findOwnerByPhoneNumber(String phoneNumber);

    Long saveOwner(RealPropertyOwner owner);

    void updateOwner(Long id, RealPropertyOwner owner);

    void deleteOwnerById(Long id);
}
