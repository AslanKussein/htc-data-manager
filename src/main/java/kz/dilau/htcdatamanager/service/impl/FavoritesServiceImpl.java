package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.exception.SecurityException;
import kz.dilau.htcdatamanager.repository.FavoritesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.repository.filter.FavoriteSpecifications;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.client.ClientDeviceDto;
import kz.dilau.htcdatamanager.web.dto.client.FavoritFilterDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final RealPropertyRepository realPropertyRepository;
    private final KeycloakService keycloakService;

    @Override
    public FavoritesDto getByRealPropertyId(String token, String clientLogin, String deviceUuid ,Long realPropertyId) {
        Favorites favorites = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);
        return new FavoritesDto(favorites);
    }

    private Favorites getFavoritesByClientLoginAndRealPropertyId(String token, String clientLogin, String deviceUuid, Long realPropertyId) {
        List<String> uuids = getDeviceUuids(token, clientLogin , deviceUuid) ;
        Specification<Favorites> specs = FavoriteSpecifications.filter(clientLogin, Boolean.FALSE, uuids, realPropertyId);

        Optional<Favorites> favoritesOptional = favoritesRepository.findOne(specs);
        if (!favoritesOptional.isPresent()) {
            throw NotFoundException.createFavoritesNotFoundByRealPropertyId(realPropertyId);
        }
        return favoritesOptional.get();
    }

    private List<String> getDeviceUuids(String token, String clientLogin , String deviceUuid){
        List<ClientDeviceDto> lst = keycloakService.getDevices(token, deviceUuid);
        List<String> lstUuids = new ArrayList<>();
        if (!lst.isEmpty()) {
            for (ClientDeviceDto d : lst) {
                if (nonNull(deviceUuid) && nonNull(clientLogin)) {
                    if (nonNull(d.getClientLogin()) && !d.getClientLogin().toLowerCase().equals(clientLogin.toLowerCase()))  {
                        throw BadRequestException.createTemplateException("error.has.not.permission");
                    }
                }
                lstUuids.add(d.getDeviceUuid());
            }
        }
        return lstUuids;
    }

    private FavoritesDto mapFavoritesToFavoritesDto(Favorites favorites) {
        return new FavoritesDto(favorites);
    }

    @Override
    public Page<FavoritesDto> getAllPageableByClientLogin(String token,
                                                        String clientLogin,
                                                        FavoritFilterDto filterDto) {
        if (isNull(clientLogin) && (isNull(filterDto) || isNull(filterDto.getDeviceUuid()))) {
            throw BadRequestException.createRequiredIsEmpty("deviceUuid");
        }
        List<String> uuids = getDeviceUuids(token, clientLogin , filterDto.getDeviceUuid());
        Specification<Favorites> specification;
        if (nonNull(filterDto.getDeviceUuid())) {
            specification = FavoriteSpecifications.filter(clientLogin, Boolean.TRUE,uuids, null);
        } else {
            specification = FavoriteSpecifications.filter(clientLogin, Boolean.FALSE,uuids, null);
        }

        Page<Favorites> favorites = favoritesRepository.findAll(specification, PageableUtils.createPageRequest(filterDto));

        return favorites.map(this::mapFavoritesToFavoritesDto);
    }


    @Override
    public FavoritesDto save(String token, String clientLogin, String deviceUuid,Long realPropertyId) {
        Favorites favorites;

        try {
            favorites = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);
            return new FavoritesDto(favorites);
        }catch (NotFoundException e){
            favorites = new Favorites();
        }

        Optional<RealProperty> realProperty = realPropertyRepository.findById(realPropertyId);
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(realPropertyId);
        }

        if (nonNull(clientLogin)) favorites.setClientLogin(clientLogin);
        if (nonNull(deviceUuid)) favorites.setDeviceUuid(deviceUuid);
        favorites.setRealProperty(realProperty.get());
        favorites.setCreateDate(new Timestamp(new Date().getTime()));
        favoritesRepository.save(favorites);
        return new FavoritesDto(favorites);
    }

    @Override
    public void deleteByRealPropertyId(String token,String clientLogin, String deviceUuid, Long realPropertyId) {
        Favorites favorites = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);

        favoritesRepository.delete(favorites);
    }


    @Override
    public List<Long> getAllByClientLogin(String token, String clientLogin) {
        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false);

        Specification<Favorites> specInner = FavoriteSpecifications.clientLoginEquals(clientLogin);
        List<String> lstUuids = getDeviceUuids(token, clientLogin, null);
        specification = specification.and(specInner.or(FavoriteSpecifications.deviceUuidIn(lstUuids)));

        List<Favorites> favoritesList = favoritesRepository.findAll(specification);


        return favoritesList.stream().map(item -> item.getRealPropertyId()).collect(Collectors.toList());
    }

    @Override
    public List<Long> getAllByDevice(String deviceUuid) {
        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false);
        specification = specification.and(FavoriteSpecifications.deviceUuidEquals(deviceUuid));
        List<Favorites> favoritesList = favoritesRepository.findAll(specification);
        return favoritesList.stream().map(item -> item.getRealPropertyId()).collect(Collectors.toList());
    }
}
