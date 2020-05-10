package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.domain.dictionary.AllDict;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
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
            return loadDictionariesFromDatabase("ApplicationStatus", filterDto.getPageableDto());
        } else {
            if (isNull(applicationStatusList)) {
                applicationStatusList = loadDictionariesFromDatabase("ApplicationStatus");
            }
        }
        return applicationStatusList;
    }

    public BaseCustomDictionary getApplicationStatusById(Long id) {
        BaseCustomDictionary one = loadDictionaryByIdFromDatabase("ApplicationStatus", id);
        return one;
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
        BaseCustomDictionary one = loadDictionaryByIdFromDatabase("AllDict", id);
        return one;
    }

    public Long getAllDictListCount() {
        return loadDictionariesCountFromDatabase("AllDict");
    }

    public List<Street> getStreetList(DictionaryFilterDto filterDto) {
        if (nonNull(filterDto) && nonNull(filterDto.getPageableDto())) {
            return loadDictionariesFromDatabase("Street", filterDto.getPageableDto());
        } else {
            if (isNull(streetList)) {
                streetList = loadDictionariesFromDatabase("Street");
            }
        }
        return streetList;
    }

    public BaseCustomDictionary getStreetById(Long id) {
        BaseCustomDictionary one = loadDictionaryByIdFromDatabase("Street", id);
        return one;
    }

    public Long getStreetListCount() {
        return loadDictionariesCountFromDatabase("Street");
    }

    public List<BaseCustomDictionary> getDictionary(String dictionaryName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getDictMethod = "get" + dictionaryName + "List";
        Method method = this.getClass().getMethod(getDictMethod, DictionaryFilterDto.class);
        return (List<BaseCustomDictionary>) method.invoke(this, new DictionaryFilterDto());
    }

    public BaseCustomDictionary getDictionaryById(String dictionaryName, Long id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

        Optional<AllDict> first = getAllDictList(null).stream().filter(item -> item.getCode().equals(filterDto.getDictionaryName())).findFirst();
        boolean editable = false;
        if (first.isPresent()) {
            editable = first.get().getIsEditable();
        }

        PageDto<BaseCustomDictionary> listPage = new PageDto(list, filterDto.getPageableDto().getPageNumber(), filterDto.getPageableDto().getPageSize(), count, editable);
        return listPage;
    }

    public BaseCustomDictionary getDictionaryItem(String dictionaryName, Long id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseCustomDictionary dictionary = getDictionaryById(dictionaryName, id);
        return dictionary;
    }

    public List loadDictionariesFromDatabase(String dictionaryEntityName) {
        return entityManager.createQuery("from " + dictionaryEntityName + (isSystem(dictionaryEntityName) ? " where isRemoved = false" : "") + " order by id")
                .getResultList();
    }

    public List loadDictionariesFromDatabase(String dictionaryEntityName, PageableDto filterDto) {
        if (isNull(filterDto)) {
            return loadDictionariesFromDatabase(dictionaryEntityName);
        }
        return entityManager.createQuery("from " + dictionaryEntityName + (isSystem(dictionaryEntityName) ? " where isRemoved = false" : "") + " order by " + filterDto.getSortBy() + " " + filterDto.getDirection())
                .setFirstResult(filterDto.getPageSize() * filterDto.getPageNumber())
                .setMaxResults(filterDto.getPageSize())
                .getResultList();
    }

    private BaseCustomDictionary loadDictionaryByIdFromDatabase(String dictionaryEntityName, Long id) {
        return (BaseCustomDictionary) entityManager.createQuery("from " + dictionaryEntityName + " where id = " + id).getSingleResult();
    }

    public Long loadDictionariesCountFromDatabase(String dictionaryEntityName) {
        return (Long) entityManager.createQuery("select count(*) from " + dictionaryEntityName + (isSystem(dictionaryEntityName) ? " where isRemoved = false" : ""))
                .getSingleResult();
    }

    private boolean isSystem(String dictionaryName) {
        Optional<AllDict> optional = getAllDictList(null).stream().filter(dict -> dict.getCode().equals(dictionaryName)).findFirst();
        if (optional.isPresent()) {
            return optional.get().getIsSystem();
        }
        return false;
    }
}
