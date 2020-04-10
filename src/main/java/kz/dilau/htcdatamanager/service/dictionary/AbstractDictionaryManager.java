package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseDictionary;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.repository.dictionary.DictionaryRepository;
import kz.dilau.htcdatamanager.web.rest.vm.dictionary.DictionaryDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractDictionaryManager<E extends BaseDictionary, R extends DictionaryRepository<E>> implements DictionaryManager {
    protected final R repository;

    @Override
    public DictionaryDto getDictionaryById(Long id) {
        BaseDictionary dictionary = repository.getOne(id);
        return new DictionaryDto(dictionary);
    }

    @Override
    public List<DictionaryDto> getAllDictionaries() {
        return repository
                .findAll()
                .stream()
                .map(DictionaryDto::new)
                .collect(Collectors.toList());
    }

    // TODO: 10.04.20 сохранение не работает!
    @Override
    public Long saveDictionary(DictionaryDto dictionary) {
        BaseDictionary baseDictionary = (BaseDictionary) new Object();
        MultiLang multiLang = new MultiLang();
        multiLang.setNameKz(dictionary.getNameKz());
        multiLang.setNameEn(dictionary.getNameEn());
        multiLang.setNameEn(dictionary.getNameEn());
        baseDictionary.setMultiLang(multiLang);
        E e = repository.save((E) baseDictionary);
        return e.getId();
    }

    @Override
    public void updateDictionary(Long id, DictionaryDto dictionary) {
        E entity = repository.getOne(id);
        entity.getMultiLang().setNameKz(dictionary.getNameKz());
        entity.getMultiLang().setNameEn(dictionary.getNameEn());
        entity.getMultiLang().setNameRu(dictionary.getNameRu());
        repository.save(entity);
    }

    @Override
    public void deleteDictionaryById(Long id) {
        repository.deleteById(id);
    }
}
