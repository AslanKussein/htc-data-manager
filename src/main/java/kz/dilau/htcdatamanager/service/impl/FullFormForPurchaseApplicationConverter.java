package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.repository.DataRepository;
import kz.dilau.htcdatamanager.repository.InfoRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.ApplicationConverter;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service("fullFormForPurchaseConverter")
public class FullFormForPurchaseApplicationConverter implements ApplicationConverter {
    private final EntityManager entityManager;
    private final DataRepository dataRepository;
    private final InfoRepository infoRepository;
    private final RealPropertyOwnerRepository ownerRepository;
    private final RealPropertyRepository realPropertyRepository;

    @Override
    public Application convertFromDto(ApplicationDto dto) {
//        final Application application;
//        final Application.ApplicationBuilder builder = Application.builder();
//        if (dto.getOperationTypeId() != null) {
//            final OperationType reference = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
//            builder.operationType(reference);
//        }
//        if (dto.getObjectTypeId() != null) {
//            final ObjectType reference = entityManager.getReference(ObjectType.class, dto.getObjectTypeId());
//            builder.objectType(reference);
//        }
//        if (dto.getPossibleReasonForBiddingId() != null) {
//            PossibleReasonForBidding reference = entityManager.getReference(PossibleReasonForBidding.class, dto.getPossibleReasonForBiddingId());
//            builder.possibleReasonForBidding(reference);
//        }
//
//        builder
//                .objectPriceFrom(dto.getObjectPriceFrom())
//                .objectPriceTo(dto.getObjectPriceTo())
//                .mortgage(dto.getMortgage())
//                .probabilityOfBidding(dto.getProbabilityOfBidding())
//                .contractPeriod(dto.getContractPeriod())
//                .amount(dto.getAmount())
//                .isCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice())
//                .note(dto.getNote());
//        final RealProperty realProperty = new RealProperty();
//
//        final Data2 data = new Data2();
//        data.setFloorFrom(dto.getFloorFrom());
//        data.setFloorTo(dto.getFloorTo());
//        data.setNumberOfRoomsFrom(dto.getNumberOfRoomsFrom());
//        data.setNumberOfRoomsTo(dto.getNumberOfRoomsTo());
//        data.setTotalAreaFrom(dto.getTotalAreaFrom());
//        data.setTotalAreaTo(dto.getTotalAreaTo());
//        data.setLivingAreaFrom(dto.getLivingAreaFrom());
//        data.setLivingAreaTo(dto.getLivingAreaTo());
//        data.setKitchenAreaFrom(dto.getKitchenAreaFrom());
//        data.setKitchenAreaTo(dto.getKitchenAreaTo());
//        data.setBalconyAreaFrom(dto.getBalconyAreaFrom());
//        data.setBalconyAreaTo(dto.getBalconyAreaTo());
//        data.setCeilingHeightFrom(dto.getCeilingHeightFrom());
//        data.setCeilingHeightTo(dto.getCeilingHeightTo());
//        data.setNumberOfBedroomsFrom(dto.getNumberOfBedroomsFrom());
//        data.setNumberOfBedroomsTo(dto.getNumberOfBedroomsTo());
//        dataRepository.save(data);
//        realProperty.setData(data);
//
//        final Info info = new Info();
//        if (dto.getCityId() != null) {
//            City reference = entityManager.getReference(City.class, dto.getCityId());
//            info.setCity(reference);
//        }
//        realProperty.setAtelier(dto.getAtelier());
//        realProperty.setSeparateBathroom(dto.getSeparateBathroom());
//
//        if (dto.getDistrictId() != null) {
//            District reference = entityManager.getReference(District.class, dto.getDistrictId());
//            info.setDistrict(reference);
//        }
//
//        info.setNumberOfFloors(dto.getNumberOfFloors());
//        info.setApartmentsOnTheSite(dto.getApartmentsOnTheSite());
//        info.setMaterialOfConstruction(dto.getMaterialOfConstruction());
//        info.setYearOfConstruction(dto.getYearOfConstruction());
//        info.setTypeOfElevator(dto.getTypeOfElevator());
//        info.setConcierge(dto.getConcierge());
//        info.setWheelchair(dto.getWheelchair());
//        if (dto.getParkingTypeId() != null) {
//            ParkingType reference = entityManager.getReference(ParkingType.class, dto.getParkingTypeId());
//            info.setParkingType(reference);
//        }
//        info.setYardType(dto.getYardType());
//        info.setPlayground(dto.getPlayground());
//        infoRepository.save(info);
//
//        realProperty.setInfo(info);
//
//        final RealPropertyOwner owner;
//        if (dto.getClientId() != null) {
//            owner = ownerRepository.getOne(dto.getClientId());
//        } else {
//            owner = new RealPropertyOwner();
//            owner.setFirstName(dto.getFirstName());
//            owner.setSurname(dto.getSurname());
//            owner.setPatronymic(dto.getPatronymic());
//            owner.setPhoneNumber(dto.getPhoneNumber());
//            owner.setEmail(dto.getEmail());
//            owner.setGender(dto.getGender());
//            ownerRepository.save(owner);
//        }
//
//        realPropertyRepository.save(realProperty);
//
//        builder.owner(owner).realProperty(realProperty);
//
//        return builder.build();
        return null;
    }
}
