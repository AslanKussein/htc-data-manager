package kz.dilau.htcdatamanager.repository;

import kz.dilau.htcdatamanager.domain.BaseDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DictionaryRepository<T extends BaseDictionary, ID> extends JpaRepository<T, ID> {
}
