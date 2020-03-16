package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.BaseDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DictionaryRepository<T extends BaseDictionary, ID> extends JpaRepository<T, ID> {
}
