package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.FavoritesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final RealPropertyRepository realPropertyRepository;

    @Override
    public FavoritesDto getByRealPropertyId(String clientLogin, Long realPropertyId) {
        Favorites favorites = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        return new FavoritesDto(favorites);
    }

    @Override
    public List<FavoritesDto> getByClientLogin(String clientLogin,
                                               PageableDto pageableDto) {
        List<Favorites> list = favoritesRepository
                .findAllByClientLogin(clientLogin, PageableUtils.createPageRequest(pageableDto));

        List<FavoritesDto> favoritesDtoList = new ArrayList<>();

        list.stream().forEach(favorites -> favoritesDtoList.add(new FavoritesDto(favorites)));


        return favoritesDtoList;
    }


    @Override
    public FavoritesDto save(String clientLogin, Long realPropertyId) {

        Favorites favorites = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        if (favorites != null) {
            return new FavoritesDto(favorites);
        }

        Optional<RealProperty> realProperty = realPropertyRepository.findById(realPropertyId);
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(realPropertyId);
        }

        favorites = new Favorites();
        favorites.setClientLogin(clientLogin);
        favorites.setRealProperty(realProperty.get());
        favorites.setCreateDate(new Timestamp(new Date().getTime()));
        favoritesRepository.save(favorites);
        return new FavoritesDto(favorites);
    }

    @Override
    public void deleteByRealPropertyId(String clientLogin, Long realPropertyId) {
        Favorites favorites = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        favoritesRepository.delete(favorites);
    }


}
