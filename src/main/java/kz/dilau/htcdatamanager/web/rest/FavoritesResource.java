package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<FavoritesDto> getByRealPropertyId(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId) {
        FavoritesDto favorites = favoritesService.getByRealPropertyId(principal.getName(), realPropertyId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/getAllPageable")
    public ResponseEntity<Page<FavoritesDto>> getAllPageable(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @RequestBody PageableDto pageableDto) {
        Page<FavoritesDto> page = favoritesService.getAllPageableByClientLogin(principal.getName(), pageableDto);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/getAll")
    public ResponseEntity<List<Long>> getAll(
            @ApiIgnore @AuthenticationPrincipal final Principal principal) {
        List<Long> list = favoritesService.getAllByClientLogin(principal.getName());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<FavoritesDto> save(
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @RequestBody Long realPropertyId) {
        FavoritesDto result = favoritesService.save(principal.getName(), realPropertyId);
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

