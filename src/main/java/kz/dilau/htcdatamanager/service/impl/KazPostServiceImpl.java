package kz.dilau.htcdatamanager.service.impl;

import com.google.gson.Gson;
import kz.dilau.htcdatamanager.domain.KazPostData;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.domain.enums.KazPostDataStatus;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.KazPostDataRepository;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import kz.dilau.htcdatamanager.repository.dictionary.DistrictRepository;
import kz.dilau.htcdatamanager.repository.dictionary.StreetRepository;
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

    private KazPostDataRepository kazPostDataRepository;
    private StreetRepository streetRepository;
    private DistrictRepository districtRepository;
    private CityRepository cityRepository;

    private void setErrorStatus(KazPostData data) {
        data.setStatus(KazPostDataStatus.ERROR);
        kazPostDataRepository.save(data);
        throw BadRequestException.createRequiredIsEmpty(data.getId());
    }

    @Override
    public Boolean processingData(String jsonString) {
        Gson g = new Gson();
        KazPostDTO dto = g.fromJson(jsonString, KazPostDTO.class);
        boolean result = kazPostDataRepository.existsByIdAndStatus(dto.getPostcode(), KazPostDataStatus.FINISHED);
        if (!result) {
            KazPostData data = new KazPostData();
            data.setStatus(KazPostDataStatus.PROCESSING);
            data.setValue(jsonString);
            data = kazPostDataRepository.saveAndFlush(data);
            fillData(data, dto.getParts());
            data.setStatus(KazPostDataStatus.FINISHED);
            kazPostDataRepository.save(data);
        }
        return result;
    }

    private void fillData(KazPostData kazPostData, List<KazPostDTO.Parts> parts) {
        KazPostDTO.Parts cityData = parts.get(2);
        City city = getCity(cityData.getType());
        if (city == null) {
            setErrorStatus(kazPostData);
        }
        KazPostDTO.Parts districtData = parts.get(1);
        District district = getDistrict(districtData.getType(), city);
        if (district == null) {
            setErrorStatus(kazPostData);
        }
        KazPostDTO.Parts streetData = parts.get(0);
        Street street = getStreet(streetData.getType(), district);
        if (street == null) {
            setErrorStatus(kazPostData);
        }
    }

    private City getCity(KazPostDTO.Type type) {
        Optional<City> optionalCity = cityRepository.findByKazPostId(type.getId());
        if (!optionalCity.isPresent()) {
            optionalCity = cityRepository.findOne(getMultiLangLikeSpecification(type));
        }
        return optionalCity.orElseGet(() -> saveCity(type));
    }

    private District getDistrict(KazPostDTO.Type type, City city) {
        Optional<District> optionalDistrict = districtRepository.findByKazPostId(type.getId());
        if (!optionalDistrict.isPresent()) {
            optionalDistrict = districtRepository.findOne(getMultiLangLikeSpecification(type));
        }
        return optionalDistrict.orElseGet(() -> saveDistrict(type, city));
    }

    private Street getStreet(KazPostDTO.Type type, District district) {
        Optional<Street> optionalStreet = streetRepository.findByKazPostId(type.getId());
        if (!optionalStreet.isPresent()) {
            optionalStreet = streetRepository.findOne(getMultiLangLikeSpecification(type));
        }
        return optionalStreet.orElseGet(() -> saveStreet(type, district));
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

    private District saveDistrict(KazPostDTO.Type type, City city) {
        District district = new District();
        district.setMultiLang(fillMultiLang(type));
        district.setKazPostId(type.getId());
        district.setCity(city);
        return districtRepository.saveAndFlush(district);
    }

    private Street saveStreet(KazPostDTO.Type type, District district) {
        Street street = new Street();
        street.setMultiLang(fillMultiLang(type));
        street.setKazPostId(type.getId());
        street.setDistrict(district);
        return streetRepository.saveAndFlush(street);
    }

    private City saveCity(KazPostDTO.Type type) {
        City city = new City();
        city.setMultiLang(fillMultiLang(type));
        city.setKazPostId(type.getId());
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
