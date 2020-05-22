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
import kz.dilau.htcdatamanager.service.KazPostService;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
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

    private void setErrorStatus(KazPostData data) {
        data.setStatus(KazPostDataStatus.ERROR);
        kazPostDataRepository.save(data);
        throw BadRequestException.createRequiredIsEmpty(data.getId());
    }

    @Override
    public KazPostData processingData(String jsonString) {
        Gson g = new Gson();
        KazPostDTO dto = g.fromJson(jsonString, KazPostDTO.class);
        KazPostData kazPostData = kazPostDataRepository.findByIdAndStatus(dto.getPostcode(), KazPostDataStatus.FINISHED);
        if (kazPostData == null) {
            kazPostData = new KazPostData();
            kazPostData.setStatus(KazPostDataStatus.PROCESSING);
            kazPostData.setValue(jsonString);
            kazPostData = kazPostDataRepository.saveAndFlush(kazPostData);
            fillData(kazPostData, dto.getParts());
            kazPostData.setStatus(KazPostDataStatus.FINISHED);
            kazPostDataRepository.save(kazPostData);
        }
        return kazPostData;
    }

    private void fillData(KazPostData kazPostData, List<KazPostDTO.Parts> parts) {
        KazPostDTO.Parts cityData = parts.get(kazPostMapperProperties.getCity());
        City city = getCity(cityData);
        if (city == null) {
            setErrorStatus(kazPostData);
        }
        KazPostDTO.Parts districtData = parts.get(kazPostMapperProperties.getDistrict());
        District district = getDistrict(districtData, city);
        if (district == null) {
            setErrorStatus(kazPostData);
        }
        KazPostDTO.Parts streetData = parts.get(kazPostMapperProperties.getStreet());
        Street street = getStreet(streetData, district);
        if (street == null) {
            setErrorStatus(kazPostData);
        }
    }

    private City getCity(KazPostDTO.Parts cityData) {
        Optional<City> optionalCity = cityRepository.findByKazPostId(cityData.getId());
        if (!optionalCity.isPresent()) {
            optionalCity = cityRepository.findOne(getMultiLangLikeSpecification(cityData.getType()));
        }
        return optionalCity.orElseGet(() -> saveCity(cityData));
    }

    private District getDistrict(KazPostDTO.Parts parts, City city) {
        Optional<District> optionalDistrict = districtRepository.findByKazPostId(parts.getId());
        if (!optionalDistrict.isPresent()) {
            optionalDistrict = districtRepository.findOne(getMultiLangLikeSpecification(parts.getType()));
        }
        return optionalDistrict.orElseGet(() -> saveDistrict(parts, city));
    }

    private Street getStreet(KazPostDTO.Parts streetData, District district) {
        Optional<Street> optionalStreet = streetRepository.findByKazPostId(streetData.getId());
        if (!optionalStreet.isPresent()) {
            optionalStreet = streetRepository.findOne(getMultiLangLikeSpecification(streetData.getType()));
        }
        return optionalStreet.orElseGet(() -> saveStreet(streetData, district));
    }

    private <T> Specification<T> getMultiLangLikeSpecification(KazPostDTO.Type type) {
        Specification<T> where;
        where = (root, criteriaQuery, cb) -> cb.or(
                cb.like(cb.lower(root.get("name_ru")), "%" + type.getNameRus().trim().toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name_kz")), "%" + type.getNameKaz().trim().toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name_en")), "%" + type.getNameLat().trim().toLowerCase() + "%")
        );
        return where;
    }

    private District saveDistrict(KazPostDTO.Parts parts, City city) {
        District district = new District();
        district.setMultiLang(fillMultiLang(parts.getType()));
        district.setKazPostId(parts.getId());
        district.setCity(city);
        return districtRepository.saveAndFlush(district);
    }

    private String saveStreetType(KazPostDTO.Type type) {
        Optional<StreetType> streetTypeOptional = streetTypeRepository.findById(type.getId());
        if (streetTypeOptional.isPresent()) {
            return streetTypeOptional.get().getId();
        } else {
            return StreetType.builder()
                    .id(type.getId())
                    .multiLang(fillMultiLang(type))
                    .build().getId();
        }
    }

    private Street saveStreet(KazPostDTO.Parts streetData, District district) {
        Street street = new Street();
        street.setMultiLang(fillMultiLang(streetData.getType()));
        street.setKazPostId(streetData.getId());
        street.setDistrict(district);
        street.setStreetTypeId(saveStreetType(streetData.getType()));
        return streetRepository.saveAndFlush(street);
    }

    private City saveCity(KazPostDTO.Parts cityData) {
        City city = new City();
        city.setMultiLang(fillMultiLang(cityData.getType()));
        city.setKazPostId(cityData.getId());
        return cityRepository.saveAndFlush(city);
    }

    private MultiLang fillMultiLang(KazPostDTO.Type type) {
        MultiLang multiLang = new MultiLang();
        multiLang.setNameEn(type.getNameLat());
        multiLang.setNameKz(type.getNameKaz());
        multiLang.setNameRu(type.getNameRus());
        return multiLang;
    }
}
