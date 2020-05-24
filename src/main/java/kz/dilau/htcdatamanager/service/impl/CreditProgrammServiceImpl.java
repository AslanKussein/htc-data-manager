package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.CreditProgramm;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.dictionary.CreditProgrammRepository;
import kz.dilau.htcdatamanager.service.CreditProgrammService;
import kz.dilau.htcdatamanager.web.dto.CreditProgrammDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CreditProgrammServiceImpl implements CreditProgrammService {
    private final CreditProgrammRepository creditProgrammRepository;

    @Override
    public CreditProgrammDto getById(Long id) {
        CreditProgramm creditProgramm = getCreditProgrammById(id);
        return new CreditProgrammDto(creditProgramm);
    }


    private CreditProgramm getCreditProgrammById(Long id) {
        Optional<CreditProgramm> optionalCreditProgramm = creditProgrammRepository.findById(id);
        if (optionalCreditProgramm.isPresent()) {
            if (optionalCreditProgramm.get().getIsRemoved()) {
                throw EntityRemovedException.createCreditProgrammRemoved(id);
            }
            return optionalCreditProgramm.get();
        } else {
            throw NotFoundException.createCreditProgrammById(id);
        }
    }

    @Override
    public List<CreditProgrammDto> getAll() {
        return creditProgrammRepository
                .findAllByRemovedFalse()
                .stream()
                .map(CreditProgrammDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CreditProgrammDto save(String token, CreditProgrammDto dto) {
        return saveCreditProgramm(new CreditProgramm(), dto);
    }

    @Override
    public CreditProgrammDto update(String token, Long id, CreditProgrammDto input) {
        CreditProgramm creditProgramm = getCreditProgrammById(id);
        return saveCreditProgramm(creditProgramm, input);
    }

    @Override
    public CreditProgrammDto deleteById(String token, Long id) {
        CreditProgramm creditProgramm = getCreditProgrammById(id);
        creditProgramm.setIsRemoved(true);
        creditProgramm = creditProgrammRepository.save(creditProgramm);
        return new CreditProgrammDto(creditProgramm);
    }

    private CreditProgrammDto saveCreditProgramm(CreditProgramm creditProgramm, CreditProgrammDto dto) {

        creditProgramm.setNameRu(dto.getNameRu());
        creditProgramm.setNameKz(dto.getNameKz());
        creditProgramm.setNameEn(dto.getNameEn());
        creditProgramm.setMaxCreditPeriod(dto.getMaxCreditPeriod());
        creditProgramm.setMinCreditPeriod(dto.getMinCreditPeriod());
        creditProgramm.setMaxDownPayment(dto.getMaxDownPayment());
        creditProgramm.setMinDownPayment(dto.getMinDownPayment());
        creditProgramm.setPercent(dto.getPercent());

        creditProgramm = creditProgrammRepository.save(creditProgramm);
        return new CreditProgrammDto(creditProgramm);
    }

}
