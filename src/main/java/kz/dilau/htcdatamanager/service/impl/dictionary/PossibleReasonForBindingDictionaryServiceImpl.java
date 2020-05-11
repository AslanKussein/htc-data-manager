package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import kz.dilau.htcdatamanager.repository.dictionary.PossibleReasonForBiddingRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("PossibleReasonForBidding")
public class PossibleReasonForBindingDictionaryServiceImpl implements LinearDictionaryService {
    private final PossibleReasonForBiddingRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new PossibleReasonForBidding(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        PossibleReasonForBidding byId = cacheService.getById(PossibleReasonForBidding.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        PossibleReasonForBidding byId = cacheService.getById(PossibleReasonForBidding.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private PossibleReasonForBidding saveDict(PossibleReasonForBidding dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
