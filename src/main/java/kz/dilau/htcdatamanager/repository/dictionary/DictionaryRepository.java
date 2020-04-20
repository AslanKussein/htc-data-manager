package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DictionaryRepository<T extends BaseCustomDictionary> extends JpaRepository<T, Long> {
}
