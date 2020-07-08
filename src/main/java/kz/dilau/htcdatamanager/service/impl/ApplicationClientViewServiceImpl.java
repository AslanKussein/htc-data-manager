package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.service.ApplicationClientViewService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientViewDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationClientViewServiceImpl implements ApplicationClientViewService {
    private final ApplicationService applicationService;
    private final NotesService notesService;
    private final DictionaryCacheService dictionaryCacheService;

    @Override
    public ApplicationClientViewDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationDto(application);
    }

    private ApplicationClientViewDto mapToApplicationDto(Application application) {
        ApplicationClientViewDto dto = new ApplicationClientViewDto();

        dto.setObjectTypeId(application.getObjectTypeId());
        dto.setClientLogin(application.getClientLogin());
        dto.setAgent(application.getCurrentAgent());
        dto.setId(application.getId());
        dto.setIsReserved(application.isReservedRealProperty());
        dto.setApplicationStatusId(application.getApplicationStatus().getId());
        dto.setContractDto(new ContractFormDto(application.getContract()));
        if (application.getApplicationSellData() != null) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            RealPropertyClientViewDto realPropertyDto = new RealPropertyClientViewDto(realProperty);
            realPropertyDto.setNotesCount(notesService.getCountByRealPropertyId(realProperty.getId()));
            dto.setRealPropertyDto(realPropertyDto);
            dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
            if (!realPropertyDto.getGeneralCharacteristicsDto().getParkingTypeIds().isEmpty()) {
                List<DictionaryDto> parkingTypeList = new ArrayList<>();
                for (Long parkingId : realPropertyDto.getGeneralCharacteristicsDto().getParkingTypeIds()) {
                    BaseCustomDictionary aDictionaryItem = dictionaryCacheService.loadDictionaryByIdFromDatabase("ParkingType", parkingId);
                    parkingTypeList.add(fillDictionaryDto(aDictionaryItem.getId(), aDictionaryItem.getMultiLang()));
                }
                realPropertyDto.getGeneralCharacteristicsDto().setParkingTypeList(parkingTypeList);
            }
        }

        return dto;
    }


    private DictionaryDto fillDictionaryDto(Long id, MultiLang multiLang) {
        return DictionaryDto.builder()
                .id(id)
                .nameEn(multiLang.getNameEn())
                .nameKz(multiLang.getNameKz())
                .nameRu(multiLang.getNameRu())
                .build();
    }
}
