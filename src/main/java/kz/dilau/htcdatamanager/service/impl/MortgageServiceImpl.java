package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.dictionary.Mortgage;
import kz.dilau.htcdatamanager.repository.dictionary.MortgageRepository;
import kz.dilau.htcdatamanager.service.MortgageService;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service
public class MortgageServiceImpl implements MortgageService {
    private final MortgageRepository mortgageRepository;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public MortgageDto save(String token, MortgageDto dto) {
        return saveMortgage(new Mortgage(), dto);
    }

    private MortgageDto saveMortgage(Mortgage mortgage, MortgageDto dto) {


        mortgage.setFio(dto.getFio());
        mortgage.setCreditSum(dto.getCreditSum());
        mortgage.setCreditTerm(dto.getCreditTerm());
        mortgage.setTotalIncome(dto.getTotalIncome());
        mortgage.setActiveCredit(dto.isActiveCredit());
        mortgage.setActiveCreditSum(dto.getActiveCreditSum());

        mortgage = mortgageRepository.save(mortgage);
        return new MortgageDto(mortgage);
    }

}
