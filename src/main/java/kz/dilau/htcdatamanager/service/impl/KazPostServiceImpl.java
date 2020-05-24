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

    @Override
    public KazPostData processingData(KazPostDTO dto) {
        return kazPostDataRepository.findByIdAndStatus(dto.getPostcode(), KazPostDataStatus.FINISHED)
                .orElse(createKazPostData(dto));
    }

    private KazPostData createKazPostData(KazPostDTO dto) {
        KazPostData kazPostData = new KazPostData();
        kazPostData.setId(dto.getPostcode());
        kazPostData.setStatus(KazPostDataStatus.PROCESSING);
        kazPostData.setValue(new Gson().toJson(dto));
        kazPostData = kazPostDataRepository.saveAndFlush(kazPostData);

        fillData(kazPostData, dto.getParts());

        kazPostData.setStatus(KazPostDataStatus.FINISHED);
        return kazPostDataRepository.save(kazPostData);
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
        return cityRepository.findByKazPostId(cityData.getId())
                .orElse(cityRepository.findOne(getMultiLangLikeSpecification(cityData.getType()))
                        .orElse(saveCity(cityData)));
    }

    private District getDistrict(KazPostDTO.Parts parts, City city) {
        return districtRepository.findByKazPostId(parts.getId())
                .orElse(districtRepository.findOne(getMultiLangLikeSpecification(parts.getType()))
                        .orElse(saveDistrict(parts, city)));
    }

    private Street getStreet(KazPostDTO.Parts streetData, District district) {
        return streetRepository.findByKazPostId(streetData.getId())
                .orElse(streetRepository.findOne(getMultiLangLikeSpecification(streetData.getType()))
                        .orElse(saveStreet(streetData, district)));
    }

    private <T> Specification<T> getMultiLangLikeSpecification(KazPostDTO.Type type) {
        return (root, criteriaQuery, cb) -> cb.or(
                cb.like(cb.lower(root.get("name_ru")), "%" + type.getNameRus().trim().toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name_kz")), "%" + type.getNameKaz().trim().toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name_en")), "%" + type.getNameLat().trim().toLowerCase() + "%")
        );
    }

    private District saveDistrict(KazPostDTO.Parts parts, City city) {
        District district = new District();
        district.setMultiLang(fillMultiLang(parts.getType()));
        district.setKazPostId(parts.getId());
        district.setCity(city);
        return districtRepository.saveAndFlush(district);
    }

    private String saveStreetType(KazPostDTO.Type type) {
        return streetTypeRepository.findById(type.getId())
                .orElse(buildStreetType(type))
                .getId();
    }

    private StreetType buildStreetType(KazPostDTO.Type type) {
        return streetTypeRepository.save(StreetType.builder()
                .id(type.getId())
                .multiLang(fillMultiLang(type))
                .build());
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
