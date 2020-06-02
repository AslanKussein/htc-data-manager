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
        Optional<Favorites> favorites = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        if (!favorites.isPresent()) {
            throw NotFoundException.createFavoritesNotFoundByRealPropertyId(realPropertyId);
        }

        return new FavoritesDto(favorites.get());
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

        Favorites favorites;
        Optional<Favorites> favoritesOptional = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        if (favoritesOptional.isPresent()) {
            return new FavoritesDto(favoritesOptional.get());
        } else {
            favorites = new Favorites();
        }

        Optional<RealProperty> realProperty = realPropertyRepository.findById(realPropertyId);
        if (!realProperty.isPresent()) {
            throw NotFoundException.createRealPropertyNotFoundById(realPropertyId);
        }

        favorites.setClientLogin(clientLogin);
        favorites.setRealProperty(realProperty.get());
        favorites.setCreateDate(new Timestamp(new Date().getTime()));
        favoritesRepository.save(favorites);
        return new FavoritesDto(favorites);
    }

    @Override
    public void deleteByRealPropertyId(String clientLogin, Long realPropertyId) {
        Optional<Favorites> favorites = favoritesRepository.findByRealProperty_IdAndClientLogin(realPropertyId, clientLogin);
        if (!favorites.isPresent()) {
            throw NotFoundException.createFavoritesNotFoundByRealPropertyId(realPropertyId);
        }
        favoritesRepository.delete(favorites.get());
    }


}
