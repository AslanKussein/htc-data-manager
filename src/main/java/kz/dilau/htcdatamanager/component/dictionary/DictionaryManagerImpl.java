package kz.dilau.htcdatamanager.component.dictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DictionaryManagerImpl implements DictionaryManager {
    private final DictionaryDao dictionaryDao;

    @Override
    public DictionaryDto<Long> getById(String token, Dictionary dictionary, Long id) {
        DictionaryDto<Long> dto = dictionaryDao.getByIdFromTable(id, dictionary.getTableName());
        return dto;
    }

    @Override
    public List<DictionaryDto<Long>> getAll(String token, Dictionary dictionary) {
        List<DictionaryDto<Long>> dictionaries = dictionaryDao.getByAllFromTable(dictionary.getTableName());
        return dictionaries;
    }

    @Override
    public Long save(String token, Dictionary dictionary, DictionaryDto<Long> input) {
        Long id = null;
        if (dictionary == Dictionary.POSSIBLE_REASONS_FOR_BIDDING) {
            id = dictionaryDao.savePossibleReasonForBidding(dictionary, input);
        } else {
            if (dictionary.getType() == DictionaryType.CUSTOM) {
                id = dictionaryDao.saveCustomDictionary(dictionary, input);
            } else {
                id = dictionaryDao.saveSystemDictionary(dictionary, input);
            }
        }
        return id;
    }

    @Override
    public void update(String token, Dictionary dictionary, Long id, DictionaryDto<Long> input) {
        Integer updated = dictionaryDao.update(dictionary, id, input);
    }

    @Override
    public void deleteById(String token, Dictionary dictionary, Long id) {
        Integer deleted = dictionaryDao.deleteByIdFromTable(id, dictionary.getTableName());
    }
}
