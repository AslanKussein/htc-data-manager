package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.FAVORITES_REST_ENDPOINT)
public class FavoritesResource {
    private final FavoritesService favoritesService;

    @GetMapping("/{realPropertyId}")
    public ResponseEntity<Favorites> getByRealPropertyId(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId) {
        Favorites favorites = favoritesService.getByRealPropertyId(principal.getName(), realPropertyId);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping
    public ResponseEntity<List<Favorites>> getAll(String clientLogin) {
        List<Favorites> list = favoritesService.getByClientLogin(clientLogin);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Favorites> save(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            Long realPropertyId) {
        Favorites result = favoritesService.save(principal.getName(), realPropertyId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{realPropertyId}")
    public ResponseEntity<Favorites> deleteByRealPropertyId(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId) {
        favoritesService.deleteByRealPropertyId(principal.getName(), realPropertyId);
        return ResponseEntity.ok(null);
    }

}

