package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.repository.FavoritesRepository;
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

@RequiredArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepository favoritesRepository;

    @Override
    public Favorites getByRealPropertyId(String clientLogin, Long realPropertyId) {
        return favoritesRepository.findByRealPropertyIdAndClientLogin(realPropertyId, clientLogin);
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
    public Favorites save(String clientLogin, Long realPropertyId) {
        Favorites favorites = new Favorites();
        favorites.setClientLogin(clientLogin);
        favorites.setRealPropertyId(realPropertyId);
        favorites.setCreateDate(new Timestamp(new Date().getTime()));
        return favoritesRepository.save(favorites);
    }

    @Override
    public void deleteByRealPropertyId(String clientLogin, Long realPropertyId) {
        Favorites favorites = getByRealPropertyId(clientLogin, realPropertyId);
        favoritesRepository.delete(favorites);
    }


}
