package kz.dilau.htcdatamanager.repository.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.Mortgage;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MortgageRepository extends PagingAndSortingRepository<Mortgage, Long> {


}
