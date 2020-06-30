package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.ApplicationDeposit;
import kz.dilau.htcdatamanager.domain.dictionary.PayType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationClientPayService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientPayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationClientPayServiceImpl implements ApplicationClientPayService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final EntityService entityService;

    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Override
    public ApplicationClientDTO update(Long id, ApplicationClientPayDTO dto) {
        Application application = applicationService.getApplicationById(id);

        if (!application.getOperationType().isSell()) {
            throw BadRequestException.createTemplateException("error.only.sell.application.can.deposit");
        }
        if (nonNull(application.getSellDeposit())) {
            throw BadRequestException.applicationPayed(id);
        }

        application.setSellDeposit(ApplicationDeposit.builder()
                .sellApplication(application)
                .payedSum(dto.getPayedSum())
                .payType(entityService.mapRequiredEntity(PayType.class, dto.getPayTypeId()))
                .payedClientLogin(getAuthorName())
                .build());

        applicationRepository.save(application);

        return new ApplicationClientDTO(application);
    }
}
