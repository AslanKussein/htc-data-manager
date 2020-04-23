package kz.dilau.htcdatamanager.service.dictionary;

public interface AbstractDictionary {
    String getTableName();

    DictionaryType getType();
}
