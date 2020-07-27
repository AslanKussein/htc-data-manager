package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.client.FavoritFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

import static kz.dilau.htcdatamanager.config.Constants.API_REST_ENDPOINT;
import static kz.dilau.htcdatamanager.config.Constants.FAVORITES_REST_ENDPOINT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_REST_ENDPOINT + FAVORITES_REST_ENDPOINT)
public class FavoritesResource {
    private final FavoritesService favoritesService;

    @RequestMapping(value = {"/{realPropertyId}/{device_uuid}", "/{realPropertyId}"}, method = RequestMethod.GET)
    public ResponseEntity<FavoritesDto> getByRealPropertyId(
            @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId,
            @PathVariable(value = "device_uuid" ,required = false) String deviceUuid) {
        FavoritesDto favorites = favoritesService.getByRealPropertyId(token, principal.getName(), deviceUuid, realPropertyId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/getAllPageable")
    public ResponseEntity<Page<FavoritesDto>> getAllPageable(
            @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @RequestBody FavoritFilterDto pageableDto) {
        Page<FavoritesDto> page = favoritesService.getAllPageableByClientLogin(token, principal.getName(), pageableDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Long>> getAll(
            @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
            @ApiIgnore @AuthenticationPrincipal final Principal principal) {
        List<Long> list = favoritesService.getAllByClientLogin(token, principal.getName());
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = {"/{realPropertyId}/{device_uuid}", "/{realPropertyId}"}, method = RequestMethod.POST)
    public ResponseEntity<FavoritesDto> save(
            @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId,
            @PathVariable(value = "device_uuid" ,required = false) String deviceUuid) {
        FavoritesDto result = favoritesService.save(token, principal.getName(), deviceUuid, realPropertyId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{realPropertyId}")
    public ResponseEntity<Favorites> deleteByRealPropertyId(
            @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
            @ApiIgnore @AuthenticationPrincipal final Principal principal,
            @PathVariable("realPropertyId") Long realPropertyId) {
        favoritesService.deleteByRealPropertyId(token, principal.getName(), null, realPropertyId);
        return ResponseEntity.ok(null);
    }

}

