package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.old.OldRealProperty;
import kz.dilau.htcdatamanager.domain.old.OldPurchaseInfo;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyRequestDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientDto;

import java.util.List;

public interface RealPropertyService {
    RealPropertyRequestDto getById(Long id);

    List<RealPropertyRequestDto> getAll();

    void deleteById(Long id);

    void update(Long id, OldRealProperty var0);

    void save(OldRealProperty realProperty);

    void addFilesToProperty(Long propertyId, List<String> photoIds, List<String> housingPlans, List<String> virtualTours);

    RealPropertyRequestDto mapToRealPropertyDto(OldRealProperty realProperty);

    RealPropertyClientDto mapToRealPropertyClientDto(OldRealProperty realProperty);

    boolean existsByCadastralNumber(String cadastralNumber);

    List<String> mapPhotoList(OldRealProperty realProperty, RealPropertyFileType fileType);

    PurchaseInfoDto mapToPurchaseInfoDto(OldPurchaseInfo info);
}
