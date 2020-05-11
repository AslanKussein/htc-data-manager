package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.domain.dictionary.AllDict;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryCacheService;
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

    private List<ApplicationStatus> applicationStatusList;
    private List<AllDict> allDictList;
    private List<Street> streetList;

    public void clearDictionaries() {
        applicationStatusList = null;
        allDictList = null;
        streetList = null;
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

    public Long getApplicationStatusListCount() {
        return loadDictionariesCountFromDatabase("ApplicationStatus");
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

    public Long getAllDictListCount() {
        return loadDictionariesCountFromDatabase("AllDict");
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

    public Long getStreetListCount() {
        return loadDictionariesCountFromDatabase("Street");
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

        String getDictCountMethod = "get" + filterDto.getDictionaryName() + "ListCount";
        Method countMethod = this.getClass().getMethod(getDictCountMethod);
        Long count = (Long) countMethod.invoke(this);

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
        try {
            return (T) getDictionaryItem(clazz.getName(), id);
        } catch (Exception e) {
            throw BadRequestException.idMustNotBeNull();
        }
    }
}
