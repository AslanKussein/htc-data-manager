package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationConverter;
import kz.dilau.htcdatamanager.service.ApplicationManager;
import kz.dilau.htcdatamanager.service.DataAccessManager;
import kz.dilau.htcdatamanager.web.rest.vm.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ApplicationManagerImpl implements ApplicationManager {
    private final ApplicationContext appContext;
    private final ApplicationRepository applicationRepository;
    private final DataAccessManager dataAccessManager;

    @Transactional
    public Long saveApplication(final String token,
                                final ApplicationType applicationType,
                                final ApplicationDto dto) {
        //todo validate inputs
        final ApplicationConverter converter = appContext.getBean(applicationType.getConverterName(), ApplicationConverter.class);
        final Application application = converter.convertFromDto(dto);
        Long id = applicationRepository.save(application).getId();
        return id;
    }

    @Override
    public List<RecentlyCreatedApplication> getRecentlyCreatedApps(final String token) {
        List<Application> recentlyCreatedApps = applicationRepository.getRecentlyCreatedApps();
        return recentlyCreatedApps.stream().map(e -> RecentlyCreatedApplication
                .builder()
                .id(e.getId())
                .date(e.getCreatedDate())
                .operationTypeId(e.getOperationType().getId())
                .clientFullName(new StringBuilder(e.getOwner().getFirstName())
                        .append(" ")
                        .append(e.getOwner().getSurname())//todo append null string
                        .append(" ")
                        .append(e.getOwner().getPatronymic())
                        .toString())
                .phoneNumber(e.getOwner().getPhoneNumber())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ApplicationDto getById(final String token, Long id) {
        ApplicationDto dto = new ApplicationDto();
        ListResponse<CheckOperationGroupDto> checkOperationList = dataAccessManager.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
        Application application = applicationRepository.getOne(id);
        checkOperationList
                .getData()
                .stream()
                .filter(e -> "APPLICATION_GROUP".equals(e.getCode()))
                .findFirst()
                .ifPresent(e -> {
                    List<String> operations = e.getOperations();
                    for (String oper : operations) {
                        switch (oper) {
                            case "VIEW_SALE_DEAL_INFO":
                                dto.setId(application.getId());
                                dto.setOperationTypeId(application.getOperationType().getId());
                                dto.setObjectTypeId(application.getObjectType().getId());
                                dto.setObjectPrice(application.getObjectPrice());
                                dto.setMortgage(application.getMortgage());
                                dto.setEncumbrance(application.getEncumbrance());
                                dto.setSharedOwnershipProperty(application.getSharedOwnershipProperty());
                                dto.setExchange(application.getExchange());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());
                                dto.setTheSizeOfTrades(application.getTheSizeOfTrades());
                                break;
                            case "NOT_ACCESS_ VIEW_SALE_DEAL_INFO":
                                break;
                            case "VIEW_PURCHASE_DEAL_INFO":
                                dto.setOperationTypeId(application.getOperationType().getId());
                                dto.setObjectTypeId(application.getObjectType().getId());
                                dto.setObjectPriceFrom(application.getObjectPriceFrom());
                                dto.setObjectPriceTo(application.getObjectPriceTo());
                                dto.setMortgage(application.getMortgage());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());
                                break;
                            case "NOT_ACCESS_ VIEW_PURCHASE_DEAL_INFO":
                                break;
                            case "VIEW_DEAL_DATA":
                                dto.setContractPeriod(application.getContractPeriod());
                                dto.setAmount(application.getAmount());
                                dto.setCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice());
                                break;
                        }
                    }
                });
        return dto;
    }

    @Override
    public void update(Long id, ApplicationDto application) {

    }
}
