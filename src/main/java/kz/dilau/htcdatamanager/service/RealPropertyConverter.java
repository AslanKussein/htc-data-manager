package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Data;
import kz.dilau.htcdatamanager.domain.Info;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.repository.DataRepository;
import kz.dilau.htcdatamanager.repository.InfoRepository;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class RealPropertyConverter {
    private final EntityManager entityManager;
    private final DataRepository dataRepository;
    private final InfoRepository infoRepository;

    @Autowired
    public RealPropertyConverter(EntityManager entityManager, DataRepository dataRepository, InfoRepository infoRepository) {
        this.entityManager = entityManager;
        this.dataRepository = dataRepository;
        this.infoRepository = infoRepository;
    }

    //todo transaction
    public RealProperty toSell(final ApplicationDto dto) {
        final RealProperty property = new RealProperty();
        property.setCadastralNumber(dto.getCadastralNumber());
        property.setFloor(dto.getFloor());
        property.setApartmentNumber(dto.getApartmentNumber());
        property.setNumberOfRooms(dto.getNumberOfRooms());
        property.setTotalArea(dto.getTotalArea());
        property.setLivingArea(dto.getLivingArea());
        property.setKitchenArea(dto.getKitchenArea());
        property.setBalconyArea(dto.getBalconyArea());
        property.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        property.setAtelier(dto.getAtelier());
        property.setSeparateBathroom(dto.getSeparateBathroom());
        if (dto.getResidentialComplexId() != null) {
            final ResidentialComplex reference = entityManager.getReference(ResidentialComplex.class, dto.getResidentialComplexId());
            property.setResidentialComplex(reference);
        } else {
            final Info info = new Info();
            if (dto.getCityId() != null) {
                City reference = entityManager.getReference(City.class, dto.getCityId());
                info.setCity(reference);
            }
            if (dto.getStreetId() != null) {
                Street reference = entityManager.getReference(Street.class, dto.getStreetId());
                info.setStreet(reference);
            }
            info.setHouseNumber(dto.getHouseNumber());
            info.setHouseNumberFraction(dto.getHouseNumberFraction());
            info.setCeilingHeight(dto.getCeilingHeight());
            if (dto.getDistrictId() != null) {
                District reference = entityManager.getReference(District.class, dto.getDistrictId());
                info.setDistrict(reference);
            }
            info.setNumberOfFloors(dto.getNumberOfFloors());
            info.setApartmentsOnTheSite(dto.getApartmentsOnTheSite());
            info.setMaterialOfConstruction(dto.getMaterialOfConstruction());
            info.setYearOfConstruction(dto.getYearOfConstruction());
            info.setTypeOfElevator(dto.getTypeOfElevator());
            info.setConcierge(dto.getConcierge());
            info.setWheelchair(dto.getWheelchair());
            if (dto.getParkingTypeId() != null) {
                ParkingType reference = entityManager.getReference(ParkingType.class, dto.getParkingTypeId());
                info.setParkingType(reference);
            }
            info.setYardType(dto.getYardType());
            info.setPlayground(dto.getPlayground());
            infoRepository.save(info);
            property.setInfo(info);
        }
        return property;
    }

    //todo transaction
    public RealProperty toBuy(final ApplicationDto dto) {
        final RealProperty realProperty = new RealProperty();
        final Data data = new Data();
        data.setFloorFrom(dto.getFloorFrom());
        data.setFloorTo(dto.getFloorTo());
        data.setNumberOfRoomsFrom(dto.getNumberOfRoomsFrom());
        data.setNumberOfRoomsTo(dto.getNumberOfRoomsTo());
        data.setTotalAreaFrom(dto.getTotalAreaFrom());
        data.setTotalAreaTo(dto.getTotalAreaTo());
        data.setLivingAreaFrom(dto.getLivingAreaFrom());
        data.setLivingAreaTo(dto.getLivingAreaTo());
        data.setKitchenAreaFrom(dto.getKitchenAreaFrom());
        data.setKitchenAreaTo(dto.getKitchenAreaTo());
        data.setBalconyAreaFrom(dto.getBalconyAreaFrom());
        data.setBalconyAreaTo(dto.getBalconyAreaTo());
        data.setCeilingHeightFrom(dto.getCeilingHeightFrom());
        data.setCeilingHeightTo(dto.getCeilingHeightTo());
        data.setNumberOfBedroomsFrom(dto.getNumberOfBedroomsFrom());
        data.setNumberOfBedroomsTo(dto.getNumberOfBedroomsTo());
        dataRepository.save(data);
        realProperty.setData(data);

        final Info info = new Info();
        if (dto.getCityId() != null) {
            City reference = entityManager.getReference(City.class, dto.getCityId());
            info.setCity(reference);
        }
        realProperty.setAtelier(dto.getAtelier());
        realProperty.setSeparateBathroom(dto.getSeparateBathroom());

        if (dto.getDistrictId() != null) {
            District reference = entityManager.getReference(District.class, dto.getDistrictId());
            info.setDistrict(reference);
        }

        info.setNumberOfFloors(dto.getNumberOfFloors());
        info.setApartmentsOnTheSite(dto.getApartmentsOnTheSite());
        info.setMaterialOfConstruction(dto.getMaterialOfConstruction());
        info.setYearOfConstruction(dto.getYearOfConstruction());
        info.setTypeOfElevator(dto.getTypeOfElevator());
        info.setConcierge(dto.getConcierge());
        info.setWheelchair(dto.getWheelchair());
        if (dto.getParkingTypeId() != null) {
            ParkingType reference = entityManager.getReference(ParkingType.class, dto.getParkingTypeId());
            info.setParkingType(reference);
        }
        info.setYardType(dto.getYardType());
        info.setPlayground(dto.getPlayground());
        infoRepository.save(info);
        realProperty.setInfo(info);
        return realProperty;
    }
}
