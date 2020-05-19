package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientDto;

import java.util.List;

public interface RealPropertyService {
    RealPropertyDto getById(Long id);

    List<RealPropertyDto> getAll();

    void deleteById(Long id);

    void update(Long id, RealProperty var0);

    void save(RealProperty realProperty);

    void addFilesToProperty(Long propertyId, List<String> photoIds, List<String> housingPlans, List<String> virtualTours);

    RealPropertyDto mapToRealPropertyDto(RealProperty realProperty);

    RealPropertyClientDto mapToRealPropertyClientDto(RealProperty realProperty);

    boolean existsByCadastralNumber(String cadastralNumber);

    List<String> mapPhotoList(RealProperty realProperty, RealPropertyFileType fileType);

    PurchaseInfoDto mapToPurchaseInfoDto(PurchaseInfo info);
}
