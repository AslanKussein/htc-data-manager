package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationSource;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyFileRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyMetadataRepository;
import kz.dilau.htcdatamanager.service.ApplicationClientAutoCreateService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.util.EntityMappingTool;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationClientAutoCreateServiceImpl implements ApplicationClientAutoCreateService {

    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final EntityMappingTool entityMappingTool;
    private final RealPropertyMetadataRepository metadataRepository;
    private final RealPropertyFileRepository fileRepository;
    private final ApplicationService applicationService;


    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Transactional
    ApplicationClientDTO saveApplication(Application targetApplication, Application application) {


        application.setObjectType(targetApplication.getObjectType());
        application.setClientLogin(getAuthorName());
        application.setOperationType(entityService.mapEntity(OperationType.class, 1L));
        ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.FIRST_CONTACT);
        application.setApplicationStatus(status);
        application.setCurrentAgent(targetApplication.getCurrentAgent());
        ApplicationSellData sellData = targetApplication.getApplicationSellData();
        if (nonNull(sellData)) {
            RealProperty realProperty = sellData.getRealProperty();

            ApplicationPurchaseData purchaseData = new ApplicationPurchaseData();
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setObjectPriceFrom(sellData.getObjectPrice());
            purchaseInfo.setObjectPriceTo(sellData.getObjectPrice());

            if (nonNull(realProperty)) {
                RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
                if (nonNull(metadata)) {
                    purchaseInfo.setAtelier(metadata.getAtelier());
                    purchaseInfo.setBalconyAreaFrom(metadata.getBalconyArea());
                    purchaseInfo.setBalconyAreaTo(metadata.getBalconyArea());
                    purchaseInfo.setFloorFrom(metadata.getFloor());
                    purchaseInfo.setFloorTo(metadata.getFloor());
                    purchaseInfo.setKitchenAreaFrom(metadata.getKitchenArea());
                    purchaseInfo.setKitchenAreaTo(metadata.getKitchenArea());
                    purchaseInfo.setLandAreaFrom(metadata.getLandArea());
                    purchaseInfo.setLandAreaTo(metadata.getLandArea());
                    purchaseInfo.setLivingAreaFrom(metadata.getLivingArea());
                    purchaseInfo.setLivingAreaTo(metadata.getLivingArea());
                    purchaseInfo.setNumberOfBedroomsFrom(metadata.getNumberOfBedrooms());
                    purchaseInfo.setNumberOfBedroomsTo(metadata.getNumberOfBedrooms());
                    purchaseInfo.setNumberOfRoomsFrom(metadata.getNumberOfRooms());
                    purchaseInfo.setNumberOfRoomsTo(metadata.getNumberOfRooms());
                    purchaseInfo.setSeparateBathroom(metadata.getSeparateBathroom());
                    purchaseInfo.setTotalAreaFrom(metadata.getTotalArea());
                    purchaseInfo.setTotalAreaTo(metadata.getTotalArea());

                    GeneralCharacteristics generalCharacteristics = metadata.getGeneralCharacteristics();
                    if (nonNull(generalCharacteristics)) {
                        purchaseInfo.setCeilingHeightFrom(generalCharacteristics.getCeilingHeight());
                        purchaseInfo.setCeilingHeightTo(generalCharacteristics.getCeilingHeight());
                        purchaseInfo.setConcierge(generalCharacteristics.getConcierge());
                        purchaseInfo.setMaterialOfConstruction(generalCharacteristics.getMaterialOfConstruction());
                        purchaseInfo.setNumberOfFloorsFrom(generalCharacteristics.getNumberOfFloors());
                        purchaseInfo.setNumberOfFloorsTo(generalCharacteristics.getNumberOfFloors());
                        purchaseInfo.setParkingTypes(generalCharacteristics.getParkingTypes());
                        purchaseInfo.setPlayground(generalCharacteristics.getPlayground());
                        purchaseInfo.setTypesOfElevator(generalCharacteristics.getTypesOfElevator());
                        purchaseInfo.setWheelchair(generalCharacteristics.getWheelchair());
                        purchaseInfo.setYardType(generalCharacteristics.getYardType());
                        purchaseInfo.setYearOfConstructionFrom(generalCharacteristics.getYearOfConstruction());
                        purchaseInfo.setYearOfConstructionTo(generalCharacteristics.getYearOfConstruction());

                    }
                }
                if (nonNull(realProperty.getBuilding())) {
                    purchaseData.setCity(realProperty.getBuilding().getCity());
                    purchaseData.setDistrict(realProperty.getBuilding().getDistrict());
                }
            }

            purchaseData.setPurchaseInfo(purchaseInfo);
            purchaseData.setMortgage(sellData.getMortgage());
            purchaseData.setPossibleReasonsForBidding(sellData.getPossibleReasonsForBidding());
            purchaseData.setProbabilityOfBidding(sellData.getProbabilityOfBidding());
            purchaseData.setTheSizeOfTrades(sellData.getTheSizeOfTrades());

            purchaseData.setApplication(application);
            application.setApplicationPurchaseData(purchaseData);

        }

        ApplicationSource applicationSource = entityService.mapRequiredEntity(ApplicationSource.class, ApplicationSource.CA);
        application.setApplicationSource(applicationSource);
        application = applicationRepository.save(application);

        return new ApplicationClientDTO(application);
    }

    @Override
    public ApplicationClientDTO create(Long targetApplicationId) {
        Application targetApplication = applicationService.getApplicationById(targetApplicationId);
        return saveApplication(targetApplication, new Application());
    }

    @Override
    public ApplicationClientDTO update(Long id, Long targetApplicationId) {
        Application targetApplication = applicationService.getApplicationById(targetApplicationId);
        Application application = applicationService.getApplicationById(id);

        if ((nonNull(application.getApplicationPurchaseData())
                && nonNull(application.getApplicationPurchaseData().getCity()))) {
            if (isNullOrEmpty(application.getCurrentAgent()) ||
                    (!isNullOrEmpty(application.getCurrentAgent()) && !isNullOrEmpty(targetApplication.getCurrentAgent())
                            && !application.getCurrentAgent().equals(targetApplication.getCurrentAgent()))
            ) {
                application.setCurrentAgent(targetApplication.getCurrentAgent());
                application = applicationRepository.save(application);
            }
            return new ApplicationClientDTO(application);
        }

        return saveApplication(targetApplication, application);
    }
}
