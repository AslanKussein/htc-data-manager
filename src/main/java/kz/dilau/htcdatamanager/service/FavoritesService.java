package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface FavoritesService {

    FavoritesDto getByRealPropertyId(String clientLogin, Long realPropertyId);

    Page<FavoritesDto> getAllPageableByClientLogin(String clientLogin,
                                                   PageableDto pageableDto);

    FavoritesDto save(String clientLogin, Long realPropertyId);

    void deleteByRealPropertyId(String clientLogin, Long realPropertyId);

    List<Long> getAllByClientLogin(String clientLogin);

}
