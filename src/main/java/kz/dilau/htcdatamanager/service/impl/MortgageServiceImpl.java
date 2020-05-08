package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Mortgage;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.dictionary.MortgageRepository;
import kz.dilau.htcdatamanager.service.MortgageService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MortgageServiceImpl implements MortgageService {
    private final MortgageRepository mortgageRepository;

    @Override
    public MortgageDto getById(Long id) {
        Mortgage Mortgage = getMortgageById(id);
        return new MortgageDto(Mortgage);
    }


    private Mortgage getMortgageById(Long id) {
        Optional<Mortgage> optionalMortgage = mortgageRepository.findById(id);
        if (optionalMortgage.isPresent()) {
            if (optionalMortgage.get().getIsRemoved()) {
                throw EntityRemovedException.createMortgageRemoved(id);
            }
            return optionalMortgage.get();
        } else {
            throw NotFoundException.createMortgageById(id);
        }
    }

    @Override
    public List<MortgageDto> getAll() {
        return mortgageRepository
                .findAllByRemovedFalse()
                .stream()
                .map(MortgageDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<MortgageDto> getAllPageable(PageableDto dto) {
        List<MortgageDto> mortgageDtoList = new ArrayList<>();
        Page<Mortgage> mortgagePage = mortgageRepository.findPageByRemovedFalse(PageableUtils.createPageRequest(dto));
        mortgagePage.forEach(item -> mortgageDtoList.add(new MortgageDto(item)));
        return new PageDto(mortgagePage, mortgageDtoList);
    }

    @Transactional
    @Override
    public MortgageDto save(String token, MortgageDto dto) {
        return saveMortgage(new Mortgage(), dto);
    }

    @Override
    public MortgageDto update(String token, Long id, MortgageDto input) {
        Mortgage mortgage = getMortgageById(id);
        return saveMortgage(mortgage, input);
    }

    @Override
    public MortgageDto deleteById(String token, Long id) {
        Mortgage mortgage = getMortgageById(id);
        mortgage.setIsRemoved(true);
        mortgage = mortgageRepository.save(mortgage);
        return new MortgageDto(mortgage);
    }

    private MortgageDto saveMortgage(Mortgage mortgage, MortgageDto dto) {


        mortgage.setLogin(dto.getLogin());
        mortgage.setCreditSum(dto.getCreditSum());
        mortgage.setCreditTerm(dto.getCreditTerm());
        mortgage.setTotalIncome(dto.getTotalIncome());
        mortgage.setActiveCredit(dto.getActiveCredit());
        mortgage.setActiveCreditSum(dto.getActiveCreditSum());

        mortgage = mortgageRepository.save(mortgage);
        return new MortgageDto(mortgage);
    }

}
