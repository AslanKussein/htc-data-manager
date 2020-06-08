package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;

import java.util.List;


public interface FavoritesService {

    Favorites getByRealPropertyId(String clientLogin, Long realPropertyId);

    List<FavoritesDto> getAllPageableByClientLogin(String clientLogin,
                                        PageableDto pageableDto);

    Favorites save(String clientLogin, Long realPropertyId);

    void deleteByRealPropertyId(String clientLogin, Long realPropertyId);

    List<FavoritesDto> getAllByClientLogin(String clientLogin);

}
