package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.FavoritesRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.repository.filter.FavoriteSpecifications;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final RealPropertyRepository realPropertyRepository;

    @Override
    public FavoritesDto getByRealPropertyId(String clientLogin, Long realPropertyId) {
        Favorites favorites = getFavoritesByClientLoginAndRealPropertyId(clientLogin, realPropertyId);
        return new FavoritesDto(favorites);
    }

    private Favorites getFavoritesByClientLoginAndRealPropertyId(String clientLogin, Long realPropertyId) {
        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false)
                .and(FavoriteSpecifications.clientLoginEquals(clientLogin))
                .and(FavoriteSpecifications.realPropertyIdEquals(realPropertyId));

        Optional<Favorites> favoritesOptional = favoritesRepository.findOne(specification);
        if (!favoritesOptional.isPresent()) {
            throw NotFoundException.createFavoritesNotFoundByRealPropertyId(realPropertyId);
        }
        return favoritesOptional.get();
    }

    private FavoritesDto mapFavoritesToFavoritesDto(Favorites favorites) {
        return new FavoritesDto(favorites);
    }

    @Override
    public Page<FavoritesDto> getAllPageableByClientLogin(String clientLogin,
                                                          PageableDto pageableDto) {

        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false)
                .and(FavoriteSpecifications.clientLoginEquals(clientLogin));

        Page<Favorites> favorites = favoritesRepository.findAll(specification, PageableUtils.createPageRequest(pageableDto));

        return favorites.map(this::mapFavoritesToFavoritesDto);
    }


    @Override
    public FavoritesDto save(String clientLogin, Long realPropertyId) {
        Favorites favorites;

        try {
            favorites = getFavoritesByClientLoginAndRealPropertyId(clientLogin, realPropertyId);
            return new FavoritesDto(favorites);
        }catch (NotFoundException e){
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
        Favorites favorites = getFavoritesByClientLoginAndRealPropertyId(clientLogin, realPropertyId);

        favoritesRepository.delete(favorites);
    }


    @Override
    public List<Long> getAllByClientLogin(String clientLogin) {
        Specification<Favorites> specification = FavoriteSpecifications.isRemovedEquals(false)
                .and(FavoriteSpecifications.clientLoginEquals(clientLogin)) ;

        List<Favorites> favoritesList = favoritesRepository.findAll(specification);


        return favoritesList.stream().map(item -> item.getRealPropertyId()).collect(Collectors.toList());
    }

}
