package kz.dilau.htcdatamanager.service.dictionary;

import java.util.List;

public interface DictionaryDao {
    DictionaryDto<Long> getByIdFromTable(Long id, String tableName);

    List<DictionaryDto<Long>> getByAllFromTable(String tableName);

    Long savePossibleReasonForBidding(Dictionary dictionary, DictionaryDto<Long> input);

    Long saveCustomDictionary(Dictionary dictionary, DictionaryDto<Long> input);

    Long saveSystemDictionary(Dictionary dictionary, DictionaryDto<Long> input);

    Integer deleteByIdFromTable(Long id, String tableName);

    Integer update(Dictionary dictionary, Long id, DictionaryDto<Long> input);
}
