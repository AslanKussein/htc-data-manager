package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryDao dictionaryDao;

    @Override
    public DictionaryDto<Long> getById(Dictionary dictionary, Long id) {
        try {
            DictionaryDto<Long> dto = dictionaryDao.getByIdFromTable(id, dictionary.getTableName());
            return dto;
        } catch (Exception e) {
            throw NotFoundException.createEntityNotFoundById(dictionary.getTableName(), id);
        }
    }

    @Override
    public List<DictionaryDto<Long>> getAll(Dictionary dictionary) {
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
        if (updated == 0) {
            throw NotFoundException.createEntityNotFoundById(dictionary.getTableName(), id);
        }
    }

    @Override
    public void deleteById(String token, Dictionary dictionary, Long id) {
        Integer deleted = dictionaryDao.deleteByIdFromTable(id, dictionary.getTableName());
        if (deleted == 0) {
            throw NotFoundException.createEntityNotFoundById(dictionary.getTableName(), id);
        }
    }

    @Override
    public DictionaryDto<Long> getByNameAndKazPostId(Dictionary dictionary, String id) {
        try {
            return dictionaryDao.getKazPostIdFromTable(id, dictionary.getTableName());
        } catch (Exception e) {
            throw NotFoundException.createEntityNotFoundByKazPostId(dictionary.getTableName(), id);
        }
    }
}
