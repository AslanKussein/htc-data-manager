package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.service.ApplicationFullViewService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.web.dto.ApplicationFullViewDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import kz.dilau.htcdatamanager.web.dto.ContractFormFullDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationFullViewServiceImpl implements ApplicationFullViewService {
    private final ApplicationService applicationService;

    @Override
    public ApplicationFullViewDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationDto(application);
    }

    private ApplicationFullViewDto mapToApplicationDto(Application application) {
        ApplicationFullViewDto dto = new ApplicationFullViewDto();

        dto.setId(application.getId());
        dto.setOperationTypeId(application.getOperationTypeId());
        dto.setObjectTypeId(application.getObjectTypeId());
        dto.setClientLogin(application.getClientLogin());
        dto.setAgent(application.getCurrentAgent());
        dto.setIsReserved(application.isReservedRealProperty());
        dto.setApplicationStatusId(application.getApplicationStatus().getId());
        dto.setContractDto(new ContractFormFullDto(application.getContract()));
        if (application.getApplicationSellData() != null) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            if (realProperty != null) {
                dto.setRealPropertyDto(new RealPropertyDto(realProperty));
            }
            dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
        }

        return dto;
    }


}
