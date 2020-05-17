package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.old.OldApplication;
import kz.dilau.htcdatamanager.domain.old.OldGeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.old.OldPurchaseInfo;
import kz.dilau.htcdatamanager.domain.old.OldRealProperty;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.OldApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationClientService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.RealPropertyService;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.PurchaseInfoClientDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationClientServiceImpl implements ApplicationClientService {
    private final OldApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final RealPropertyService realPropertyService;

    private OldApplication getApplicationById(Long id) {
        Optional<OldApplication> optionalApplication = applicationRepository.findByIdAndIsRemovedFalse(id);
        if (optionalApplication.isPresent()) {
            return optionalApplication.get();
        } else {
            throw NotFoundException.createApplicationById(id);
        }
    }

    @Override
    public ApplicationClientDTO getById(Long id) {
        OldApplication application = getApplicationById(id);
        return fillApplicationClientDTO(application);
    }

    private ApplicationClientDTO fillApplicationClientDTO(OldApplication application) {
        return ApplicationClientDTO.builder()
                .id(application.getId())
                .operationTypeId(application.getOperationType().getId())
                .objectPrice(application.getObjectPrice())
                .probabilityOfBidding(application.getProbabilityOfBidding())
                .exchange(application.getExchange())
                .mortgage(application.getMortgage())
                .note(application.getNote())
                .realPropertyClientDto(nonNull(application.getRealProperty()) ? realPropertyService.mapToRealPropertyClientDto(application.getRealProperty()) : null)

                .build();
    }

    private OldPurchaseInfo fillPurchaseInfoDto(PurchaseInfoClientDto dto) {
        OldPurchaseInfo purchaseInfo = new OldPurchaseInfo();
        purchaseInfo.setObjectPrice(dto.getObjectPricePeriod());
        purchaseInfo.setTotalArea(dto.getTotalAreaPeriod());
        purchaseInfo.setFloor(dto.getFloorPeriod());
        return purchaseInfo;
    }

    private OldGeneralCharacteristics fillGeneralCharacteristics(RealPropertyClientDto dto) {
        if (!isNull(dto.getResidentialComplexId())) {
            return null;
        }
        return OldGeneralCharacteristics.builder()
                .district(entityService.mapEntity(District.class, dto.getDistrictId()))
                .street(entityService.mapEntity(Street.class, dto.getStreetId()))
                .houseNumber(dto.getHouseNumber())
                .houseNumberFraction(dto.getHouseNumberFraction())
                .yearOfConstruction(dto.getYearOfConstruction())
                .build();
    }

    private OldRealProperty fillRealProperty(RealPropertyClientDto dto) {
        return OldRealProperty.builder()
                .totalArea(dto.getTotalArea())
                .objectType(entityService.mapEntity(ObjectType.class, dto.getObjectTypeId()))
                .numberOfRooms(dto.getNumberOfRooms())
                .floor(dto.getFloor())
                .purchaseInfo(fillPurchaseInfoDto(dto.getPurchaseInfoClientDto()))
                .livingArea(dto.getLivingArea())
                .atelier(dto.getAtelier())
                .separateBathroom(dto.getSeparateBathroom())
                .residentialComplex(entityService.mapEntity(OldResidentialComplex.class, dto.getResidentialComplexId()))
                .generalCharacteristics(fillGeneralCharacteristics(dto))
                .build();
    }

    private void fillApplication(OldApplication application, ApplicationClientDTO dto) {
        application.setOperationType(entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId()));
        application.setProbabilityOfBidding(dto.getProbabilityOfBidding());
        application.setExchange(dto.getExchange());
        application.setMortgage(dto.getMortgage());
        application.setObjectPrice(dto.getObjectPrice());
        application.setObjectPrice(dto.getObjectPrice());
        application.setNote(dto.getNote());
        application.setRealProperty(fillRealProperty(dto.getRealPropertyClientDto()));
    }

    @Override
    public Long create(String login, ApplicationClientDTO dto) {
        OldApplication application = new OldApplication();
        application.setClientLogin(login);
        fillApplication(application, dto);

        applicationRepository.save(application);

        return application.getId();
    }

    @Override
    public Long update(Long id, ApplicationClientDTO dto) {
        OldApplication application = getApplicationById(id);

        fillApplication(application, dto);

        applicationRepository.save(application);

        return application.getId();
    }

    @Override
    public Long deleteById(Long id) {
        OldApplication application = getApplicationById(id);
        application.setIsRemoved(Boolean.TRUE);
        return applicationRepository.save(application).getId();
    }

}
