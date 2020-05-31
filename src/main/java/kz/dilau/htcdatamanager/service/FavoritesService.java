package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Favorites;

import java.util.List;


public interface FavoritesService {

    Favorites getByRealPropertyId(String clientLogin, Long realPropertyId);

    List<Favorites> getByClientLogin(String clientLogin);

    Favorites save(String clientLogin, Long realPropertyId);

    void deleteByRealPropertyId(String clientLogin, Long realPropertyId);

}
