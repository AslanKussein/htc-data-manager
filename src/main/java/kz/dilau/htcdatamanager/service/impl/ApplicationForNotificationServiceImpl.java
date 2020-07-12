package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.service.ApplicationForNotificationService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationForNotificationDto;
import kz.dilau.htcdatamanager.web.dto.ContractFormForNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationForNotificationServiceImpl implements ApplicationForNotificationService {
    private final ApplicationService applicationService;
    private final NotesService notesService;
    private final DictionaryCacheService dictionaryCacheService;

    @Override
    public ApplicationForNotificationDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationDto(application);
    }

    private ApplicationForNotificationDto mapToApplicationDto(Application application) {
        ApplicationForNotificationDto dto = new ApplicationForNotificationDto();

        dto.setClientLogin(application.getClientLogin());
        dto.setAgent(application.getCurrentAgent());
        dto.setId(application.getId());
        dto.setContract(new ContractFormForNotificationDto(application.getContract()));
        dto.setId(application.getId());
        if (application.getApplicationSellData() != null) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            dto.setAddress(DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber()));
        }
        return dto;
    }


}
