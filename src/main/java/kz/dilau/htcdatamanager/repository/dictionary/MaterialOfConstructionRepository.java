package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialOfConstructionRepository extends JpaRepository<MaterialOfConstruction, Long> {
}
