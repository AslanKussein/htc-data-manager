package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.service.ApplicationClientViewService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.NotesService;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationClientViewServiceImpl implements ApplicationClientViewService {
    private final ApplicationService applicationService;
    private final NotesService notesService;

    @Override
    public ApplicationDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationDto(application);
    }

    private ApplicationDto mapToApplicationDto(Application application) {
        ApplicationDto dto = new ApplicationDto();

        dto.setObjectTypeId(application.getObjectTypeId());
        dto.setClientLogin(application.getClientLogin());
        dto.setId(application.getId());
        if (application.getApplicationSellData() != null) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            RealPropertyDto realPropertyDto = new RealPropertyDto(realProperty);
            realPropertyDto.setNotesCount(notesService.getCountByRealPropertyId(realProperty.getId()));
            dto.setRealPropertyDto(realPropertyDto);
            dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
        }

        return dto;
    }

}
