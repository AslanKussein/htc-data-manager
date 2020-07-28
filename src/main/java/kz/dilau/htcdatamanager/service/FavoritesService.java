package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.client.FavoritFilterDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface FavoritesService {

    FavoritesDto getByRealPropertyId(String token,String clientLogin, String deviceUuid, Long realPropertyId);

    Page<FavoritesDto> getAllPageableByClientLogin(String token,
                                                   String clientLogin,
                                                   FavoritFilterDto filterDto);

    FavoritesDto save(String token, String clientLogin, String deviceUuid,Long realPropertyId);

    void deleteByRealPropertyId(String token,String clientLogin, String deviceUuid, Long realPropertyId);

    List<Long> getAllByClientLogin(String token, String clientLogin);
    List<Long> getAllByDevice(String deviceUuid);

}
