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
import kz.dilau.htcdatamanager.web.dto.client.FavoritFilterDto;
import kz.dilau.htcdatamanager.web.dto.user.UserDeviceDto;
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
        List<Favorites> favorites = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);
        return new FavoritesDto(favorites.get(0));
    }

    private List<Favorites> getFavoritesByClientLoginAndRealPropertyId(String token, String clientLogin, String deviceUuid, Long realPropertyId) {
        List<String> uuids = getDeviceUuids(token, clientLogin , deviceUuid) ;
        Specification<Favorites> specs;
        if (nonNull(deviceUuid)) {
            specs = FavoriteSpecifications.filter(clientLogin, Boolean.TRUE, uuids, realPropertyId);
        } else {
            specs = FavoriteSpecifications.filter(clientLogin, Boolean.FALSE, uuids, realPropertyId);
        }

        List<Favorites> favoritesOptional = favoritesRepository.findAll(specs);
        if (favoritesOptional.isEmpty()) {
            throw NotFoundException.createFavoritesNotFoundByRealPropertyId(realPropertyId);
        }
        return favoritesOptional;
    }

    private List<String> getDeviceUuids(String token, String clientLogin , String deviceUuid){
        List<UserDeviceDto> lst = keycloakService.getDevices(token, deviceUuid);
        List<String> lstUuids = new ArrayList<>();
        if (!lst.isEmpty()) {
            for (UserDeviceDto d : lst) {
                if (nonNull(deviceUuid) && nonNull(clientLogin)) {
                    if (nonNull(d.getLogin()) && !d.getLogin().toLowerCase().equals(clientLogin.toLowerCase()))  {
                        throw BadRequestException.createTemplateException("error.has.not.permission");
                    }
                }
                lstUuids.add(d.getDevice().getUuid());
            }
        } else {
            if (nonNull(deviceUuid)) throw BadRequestException.createTemplateException("error.has.not.permission");
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
            List<Favorites> flist = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);
            return new FavoritesDto(flist.get(0));
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
        List<Favorites> favorites = getFavoritesByClientLoginAndRealPropertyId(token, clientLogin, deviceUuid, realPropertyId);

        for (Favorites f : favorites) {
            favoritesRepository.delete(f);
        }
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
