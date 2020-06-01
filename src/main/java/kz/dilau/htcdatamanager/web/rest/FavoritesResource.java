package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
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

    @PostMapping("/getAll")
    public ResponseEntity<List<FavoritesDto>> getAll(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            PageableDto pageableDto) {
        List<FavoritesDto> list = favoritesService.getByClientLogin(principal.getName(), pageableDto);
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

