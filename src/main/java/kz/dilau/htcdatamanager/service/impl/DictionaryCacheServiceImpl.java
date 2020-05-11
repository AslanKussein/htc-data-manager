package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryFilterDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component()
@Scope(WebApplicationContext.SCOPE_APPLICATION)
public class DictionaryCacheServiceImpl implements DictionaryCacheService {
    @PersistenceContext
    EntityManager entityManager;

    private List<AllDict> allDictList;
    private List<ApplicationStatus> applicationStatusList;
    private List<City> cityList;
    private List<Country> countryList;
    private List<District> districtList;
    private List<EventType> eventTypeList;
    private List<HeatingSystem> heatingSystemList;
    private List<MaterialOfConstruction> materialOfConstructionList;
    private List<ObjectType> objectTypeList;
    private List<OperationType> operationTypeList;
    private List<ParkingType> parkingTypeList;
    private List<PossibleReasonForBidding> possibleReasonForBiddingList;
    private List<PropertyDeveloper> propertyDeveloperList;
    private List<Sewerage> sewerageList;
    private List<Street> streetList;
    private List<TypeOfElevator> typeOfElevatorList;
    private List<YardType> yardTypeList;

    public void clearDictionaries() {
        allDictList = null;
        applicationStatusList = null;
        cityList = null;
        countryList = null;
        districtList = null;
        eventTypeList = null;
        heatingSystemList = null;
        materialOfConstructionList = null;
        objectTypeList = null;
        operationTypeList = null;
        parkingTypeList = null;
        possibleReasonForBiddingList = null;
        propertyDeveloperList = null;
        sewerageList = null;
        streetList = null;
        typeOfElevatorList = null;
        yardTypeList = null;
    }

    public List<AllDict> getAllDictList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase("AllDict", filterDto.getPageableDto());
        } else {
            if (isNull(allDictList)) {
                allDictList = loadDictionariesFromDatabase("AllDict");
            }
        }
        return allDictList;
    }

    public BaseCustomDictionary getAllDictById(Long id) {
        return loadDictionaryByIdFromDatabase("AllDict", id);
    }

    public List<ApplicationStatus> getApplicationStatusList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(applicationStatusList)) {
                applicationStatusList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return applicationStatusList;
    }

    public BaseCustomDictionary getApplicationStatusById(Long id) {
        return loadDictionaryByIdFromDatabase("ApplicationStatus", id);
    }

    public List<City> getCityList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(cityList)) {
                cityList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return cityList;
    }

    public BaseCustomDictionary getCityById(Long id) {
        return loadDictionaryByIdFromDatabase("City", id);
    }

    public List<Country> getCountryList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(countryList)) {
                countryList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return countryList;
    }

    public BaseCustomDictionary getCountryById(Long id) {
        return loadDictionaryByIdFromDatabase("Country", id);
    }

    public List<District> getDistrictList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(districtList)) {
                districtList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return districtList;
    }

    public BaseCustomDictionary getDistrictById(Long id) {
        return loadDictionaryByIdFromDatabase("District", id);
    }

    public List<EventType> getEventTypeList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(eventTypeList)) {
                eventTypeList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return eventTypeList;
    }

    public BaseCustomDictionary getEventTypeById(Long id) {
        return loadDictionaryByIdFromDatabase("EventType", id);
    }

    public List<HeatingSystem> getHeatingSystemList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(heatingSystemList)) {
                heatingSystemList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return heatingSystemList;
    }

    public BaseCustomDictionary getHeatingSystemById(Long id) {
        return loadDictionaryByIdFromDatabase("HeatingSystem", id);
    }

    public List<MaterialOfConstruction> getMaterialOfConstructionList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(materialOfConstructionList)) {
                materialOfConstructionList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return materialOfConstructionList;
    }

    public BaseCustomDictionary getMaterialOfConstructionById(Long id) {
        return loadDictionaryByIdFromDatabase("MaterialOfConstruction", id);
    }

    public List<ObjectType> getObjectTypeList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(objectTypeList)) {
                objectTypeList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return objectTypeList;
    }

    public BaseCustomDictionary getObjectTypeById(Long id) {
        return loadDictionaryByIdFromDatabase("ObjectType", id);
    }

    public List<OperationType> getOperationTypeList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(operationTypeList)) {
                operationTypeList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return operationTypeList;
    }

    public BaseCustomDictionary getOperationTypeById(Long id) {
        return loadDictionaryByIdFromDatabase("OperationType", id);
    }

    public List<ParkingType> getParkingTypeList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(parkingTypeList)) {
                parkingTypeList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return parkingTypeList;
    }

    public BaseCustomDictionary getParkingTypeById(Long id) {
        return loadDictionaryByIdFromDatabase("ParkingType", id);
    }

    public List<PossibleReasonForBidding> getPossibleReasonForBiddingList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(possibleReasonForBiddingList)) {
                possibleReasonForBiddingList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return possibleReasonForBiddingList;
    }

    public BaseCustomDictionary getPossibleReasonForBiddingById(Long id) {
        return loadDictionaryByIdFromDatabase("PossibleReasonForBidding", id);
    }

    public List<PropertyDeveloper> getPropertyDeveloperList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(propertyDeveloperList)) {
                propertyDeveloperList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return propertyDeveloperList;
    }

    public BaseCustomDictionary getPropertyDeveloperById(Long id) {
        return loadDictionaryByIdFromDatabase("PropertyDeveloper", id);
    }

    public List<Sewerage> getSewerageList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(sewerageList)) {
                sewerageList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return sewerageList;
    }

    public BaseCustomDictionary getSewerageById(Long id) {
        return loadDictionaryByIdFromDatabase("Sewerage", id);
    }

    public List<Street> getStreetList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(streetList)) {
                streetList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return streetList;
    }

    public BaseCustomDictionary getStreetById(Long id) {
        return loadDictionaryByIdFromDatabase("Street", id);
    }

    public List<TypeOfElevator> getTypeOfElevatorList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(typeOfElevatorList)) {
                typeOfElevatorList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return typeOfElevatorList;
    }

    public BaseCustomDictionary getTypeOfElevatorById(Long id) {
        return loadDictionaryByIdFromDatabase("TypeOfElevator", id);
    }

    public List<YardType> getYardTypeList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase(filterDto.getDictionaryName(), filterDto.getPageableDto());
        } else {
            if (isNull(yardTypeList)) {
                yardTypeList = loadDictionariesFromDatabase(filterDto.getDictionaryName());
            }
        }
        return yardTypeList;
    }

    public BaseCustomDictionary getYardTypeById(Long id) {
        return loadDictionaryByIdFromDatabase("YardType", id);
    }

    public List<BaseCustomDictionary> getDictionary(String dictionaryName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getDictMethod = "get" + dictionaryName + "List";
        Method method = this.getClass().getMethod(getDictMethod, DictionaryFilterDto.class);
        return (List<BaseCustomDictionary>) method.invoke(this, DictionaryFilterDto.builder()
                .dictionaryName(dictionaryName)
                .build());
    }

    private BaseCustomDictionary getDictionaryById(String dictionaryName, Long id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getDictMethod = "get" + dictionaryName + "ById";
        Method method = this.getClass().getMethod(getDictMethod, Long.class);
        return (BaseCustomDictionary) method.invoke(this, id);
    }

    public PageDto<BaseCustomDictionary> getDictionary(DictionaryFilterDto filterDto) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getDictMethod = "get" + filterDto.getDictionaryName() + "List";
        Method method = this.getClass().getMethod(getDictMethod, DictionaryFilterDto.class);
        List<BaseCustomDictionary> list = (List<BaseCustomDictionary>) method.invoke(this, filterDto);

        Long count = loadDictionariesCountFromDatabase(filterDto.getDictionaryName());

        PageDto<BaseCustomDictionary> listPage = new PageDto(list, filterDto.getPageableDto().getPageNumber(), filterDto.getPageableDto().getPageSize(), count, isEditable(filterDto.getDictionaryName()));
        return listPage;
    }

    public BaseCustomDictionary getDictionaryItem(String dictionaryName, Long id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return getDictionaryById(dictionaryName, id);
    }

    private List loadDictionariesFromDatabase(String dictionaryEntityName) {
        return entityManager.createQuery("from " + dictionaryEntityName + " where isRemoved = false order by id")
                .getResultList();
    }

    private List loadDictionariesFromDatabase(String dictionaryEntityName, PageableDto filterDto) {
        if (isNull(filterDto)) {
            return loadDictionariesFromDatabase(dictionaryEntityName);
        }
        return entityManager.createQuery("from " + dictionaryEntityName + " where isRemoved = false order by " + filterDto.getSortBy() + " " + filterDto.getDirection())
                .setFirstResult(filterDto.getPageSize() * filterDto.getPageNumber())
                .setMaxResults(filterDto.getPageSize())
                .getResultList();
    }

    public BaseCustomDictionary loadDictionaryByIdFromDatabase(String dictionaryEntityName, Long id) {
        BaseCustomDictionary dictionary = (BaseCustomDictionary) entityManager.createQuery("from " + dictionaryEntityName + " where id = " + id).getSingleResult();
        if (isNull(dictionary)) {
            throw NotFoundException.createEntityNotFoundById(dictionaryEntityName, id);
        } else if (dictionary.getIsRemoved()) {
            throw EntityRemovedException.createEntityRemovedById(dictionaryEntityName, id);
        } else {
            return dictionary;
        }
    }

    private Long loadDictionariesCountFromDatabase(String dictionaryEntityName) {
        return (Long) entityManager.createQuery("select count(*) from " + dictionaryEntityName + " where isRemoved = false")
                .getSingleResult();
    }

    public boolean isEditable(String dictionaryName) {
        Optional<AllDict> optional = getAllDictList(null).stream().filter(dict -> dict.getCode().equals(dictionaryName)).findFirst();
        if (optional.isPresent()) {
            return optional.get().getIsEditable();
        }
        return false;
    }

    public <T extends BaseCustomDictionary> T getById(Class<T> clazz, Long id) {
        return (T) loadDictionaryByIdFromDatabase(clazz.getName(), id);
    }
}
