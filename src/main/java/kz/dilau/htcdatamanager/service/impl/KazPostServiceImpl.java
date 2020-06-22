package kz.dilau.htcdatamanager.service.impl;

import com.google.gson.Gson;
import kz.dilau.htcdatamanager.config.KazPostMapperProperties;
import kz.dilau.htcdatamanager.domain.KazPostData;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.domain.dictionary.StreetType;
import kz.dilau.htcdatamanager.domain.enums.KazPostDataStatus;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.KazPostDataRepository;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import kz.dilau.htcdatamanager.repository.dictionary.DistrictRepository;
import kz.dilau.htcdatamanager.repository.dictionary.StreetRepository;
import kz.dilau.htcdatamanager.repository.dictionary.StreetTypeRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.KazPostService;
import kz.dilau.htcdatamanager.service.dictionary.Dictionary;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import kz.dilau.htcdatamanager.web.dto.KazPostReturnDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class KazPostServiceImpl implements KazPostService {

    private final KazPostDataRepository kazPostDataRepository;
    private final StreetRepository streetRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final KazPostMapperProperties kazPostMapperProperties;
    private final StreetTypeRepository streetTypeRepository;
    private final DictionaryCacheService dictionaryCacheService;

    @Override
    public KazPostDTO getPostData(String postCode) {
        Optional<KazPostData> optional = kazPostDataRepository.findByIdAndStatus(postCode, KazPostDataStatus.FINISHED);
        if (optional.isPresent()) {
            return new Gson().fromJson(optional.get().getValue(), KazPostDTO.class);
        }
        return null;
    }

    @Override
    public KazPostReturnDTO processingData(KazPostDTO dto) {
        Optional<KazPostData> optionalPost = kazPostDataRepository.findByIdAndStatus(dto.getPostcode(), KazPostDataStatus.FINISHED);
        if (!optionalPost.isPresent()) {
            createKazPostData(dto);
        }
        return getDictionaryValue(dto);
    }

    private KazPostReturnDTO getDictionaryValue(KazPostDTO dto) {
        List<KazPostDTO.Parts> parts = dto.getFullAddress().getParts();
        KazPostDTO.Parts cityData = parts.get(kazPostMapperProperties.getCity());
        KazPostDTO.Parts districtData = parts.get(kazPostMapperProperties.getDistrict());
        KazPostDTO.Parts streetData = parts.get(kazPostMapperProperties.getStreet());

        Street street = streetRepository.findByKazPostId(streetData.getId());
        City city = cityRepository.findByKazPostId(cityData.getId()).get();
        District district = districtRepository.findByKazPostId(districtData.getId()).get();

        dictionaryCacheService.clearDictionaries();

        return KazPostReturnDTO.builder()
                .street(fillDictionaryDto(street.getId(), street.getMultiLang()))
                .city(fillDictionaryDto(city.getId(), city.getMultiLang()))
                .district(fillDictionaryDto(district.getId(), district.getMultiLang()))
                .houseNumber(dto.getFullAddress().getNumber())
                .build();
    }

    private DictionaryDto fillDictionaryDto(Long id, MultiLang multiLang) {
        return DictionaryDto.builder()
                .id(id)
                .nameEn(multiLang.getNameEn())
                .nameKz(multiLang.getNameKz())
                .nameRu(multiLang.getNameRu())
                .build();
    }

    private void createKazPostData(KazPostDTO dto) {
        KazPostData kazPostData = new KazPostData();
        kazPostData.setId(dto.getPostcode());
        kazPostData.setStatus(KazPostDataStatus.PROCESSING);
        kazPostData.setValue(new Gson().toJson(dto));
        kazPostData = kazPostDataRepository.saveAndFlush(kazPostData);

        fillData(kazPostData, dto.getFullAddress().getParts());

        kazPostData.setStatus(KazPostDataStatus.FINISHED);
        kazPostDataRepository.save(kazPostData);
    }

    private void fillData(KazPostData kazPostData, List<KazPostDTO.Parts> parts) {
        KazPostDTO.Parts cityData = parts.get(kazPostMapperProperties.getCity());
        KazPostDTO.Parts districtData = parts.get(kazPostMapperProperties.getDistrict());
        KazPostDTO.Parts streetData = parts.get(kazPostMapperProperties.getStreet());

        City city = getCity(cityData);
        isPresentObjectIfNotSetError(city, kazPostData);

        District district = getDistrict(districtData, city);
        isPresentObjectIfNotSetError(district, kazPostData);

        Street street = getStreet(streetData, district);
        isPresentObjectIfNotSetError(street, kazPostData);
    }

    private <X> void isPresentObjectIfNotSetError(X object, KazPostData data) {
        if (object == null) {
            data.setStatus(KazPostDataStatus.ERROR);
            kazPostDataRepository.save(data);
            throw BadRequestException.createRequiredIsEmpty(data.getId());
        }
    }

    private City getCity(KazPostDTO.Parts cityData) {
        Optional<City> optionalCity = cityRepository.findByKazPostId(cityData.getId());
        if (!optionalCity.isPresent()) {
            optionalCity = cityRepository.findOne(getMultiLangLikeSpecification(cityData));
            if (!optionalCity.isPresent()) {
                return saveCity(cityData);
            }
            City city = optionalCity.get();
            city.setKazPostId(cityData.getId());
            return cityRepository.saveAndFlush(city);
        }
        return optionalCity.get();
    }

    private <T> Specification<T> getMultiLangLikeSpecification(KazPostDTO.Parts val) {
        Specification<T> where = Specification.where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isRemoved"), false));
        return where.and((root, criteriaQuery, cb) -> cb.or(
                cb.like(cb.lower(root.get("multiLang").get("nameRu")), "%" + val.getNameRus().trim().toLowerCase() + "%"),
                cb.like(cb.lower(root.get("multiLang").get("nameKz")), "%" + val.getNameKaz().trim().toLowerCase() + "%")
        ));
    }

    private District getDistrict(KazPostDTO.Parts districtData, City city) {
        Optional<District> districtOptional = districtRepository.findByKazPostId(districtData.getId());
        if (!districtOptional.isPresent()) {
            districtOptional = districtRepository.findOne(getMultiLangLikeSpecification(districtData));
            if (!districtOptional.isPresent()) {
                return saveDistrict(districtData, city);
            }
            District district = districtOptional.get();
            district.setKazPostId(districtData.getId());
            return districtRepository.saveAndFlush(district);
        }
        return districtOptional.get();
    }

    private Street getStreet(KazPostDTO.Parts streetData, District district) {
        Optional<Street> streetOptional = streetRepository.findByKazPostIdAndStreetType_Id(streetData.getId(), streetData.getType().getId());
        if (!streetOptional.isPresent()) {
            Specification<Street> specification = getMultiLangLikeSpecification(streetData);
            specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("streetType"), streetData.getType().getId()));
            streetOptional = streetRepository.findOne(specification);
            if (!streetOptional.isPresent()) {
                return saveStreet(streetData, district);
            }
            Street street = streetOptional.get();
            street.setKazPostId(streetData.getId());
            street.setStreetType(saveStreetType(streetData.getType()));
            return streetRepository.saveAndFlush(street);
        }
        return streetOptional.get();
    }

    private District saveDistrict(KazPostDTO.Parts parts, City city) {
        District district = new District();
        district.setMultiLang(fillMultiLang(parts));
        district.setKazPostId(parts.getId());
        district.setCity(city);
        return districtRepository.saveAndFlush(district);
    }

    private StreetType saveStreetType(KazPostDTO.Type type) {
        return streetTypeRepository.findById(type.getId())
                .orElse(buildStreetType(type));
    }

    private StreetType buildStreetType(KazPostDTO.Type type) {
        return streetTypeRepository.save(StreetType.builder()
                .id(type.getId())
                .multiLang(fillMultiLang(type))
                .build());
    }

    private Street saveStreet(KazPostDTO.Parts streetData, District district) {
        Street street = new Street();
        street.setMultiLang(fillMultiLang(streetData));
        street.setKazPostId(streetData.getId());
        street.setDistrict(district);
        street.setStreetType(saveStreetType(streetData.getType()));
        return streetRepository.saveAndFlush(street);
    }

    private City saveCity(KazPostDTO.Parts cityData) {
        City city = new City();
        city.setMultiLang(fillMultiLang(cityData));
        city.setKazPostId(cityData.getId());
        return cityRepository.saveAndFlush(city);
    }

    private MultiLang fillMultiLang(KazPostDTO.Parts data) {
        MultiLang multiLang = new MultiLang();
        multiLang.setNameKz(data.getNameKaz());
        multiLang.setNameRu(data.getNameRus());
        multiLang.setNameEn(data.getNameRus());
        return multiLang;
    }

    private MultiLang fillMultiLang(KazPostDTO.Type type) {
        MultiLang multiLang = new MultiLang();
        multiLang.setNameKz(type.getNameKaz());
        multiLang.setNameRu(type.getNameRus());
        multiLang.setNameEn(type.getNameLat());
        return multiLang;
    }
}
