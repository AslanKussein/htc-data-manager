package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.Favorites;
import kz.dilau.htcdatamanager.service.FavoritesService;
import kz.dilau.htcdatamanager.web.dto.FavoritesDto;
import kz.dilau.htcdatamanager.web.dto.client.FavoritFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kz.dilau.htcdatamanager.config.Constants.OPEN_API_REST_ENDPOINT;
import static kz.dilau.htcdatamanager.config.Constants.FAVORITES_REST_ENDPOINT;

@RequiredArgsConstructor
@RestController
@RequestMapping(OPEN_API_REST_ENDPOINT + FAVORITES_REST_ENDPOINT)
public class FavoritesOpenResource {
    private final FavoritesService favoritesService;

    @RequestMapping(value = {"/{realPropertyId}/{device_uuid}", "/{realPropertyId}"}, method = RequestMethod.GET)
    public ResponseEntity<FavoritesDto> getByRealPropertyId(
            @PathVariable("realPropertyId") Long realPropertyId,
            @PathVariable("device_uuid") String deviceUuid) {
        FavoritesDto favorites = favoritesService.getByRealPropertyId(null, null, deviceUuid, realPropertyId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/getAllPageable")
    public ResponseEntity<Page<FavoritesDto>> getAllPageable(
            @RequestBody FavoritFilterDto pageableDto) {
        Page<FavoritesDto> page = favoritesService.getAllPageableByClientLogin(null, null, pageableDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/getAll/{device_uuid}")
    public ResponseEntity<List<Long>> getAll(@PathVariable("device_uuid") String deviceUuid) {
        List<Long> list = favoritesService.getAllByDevice(deviceUuid);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = {"/{realPropertyId}/{device_uuid}"}, method = RequestMethod.POST)
    public ResponseEntity<FavoritesDto> save(
            @PathVariable("realPropertyId") Long realPropertyId,
            @PathVariable("device_uuid") String deviceUuid) {
        FavoritesDto result = favoritesService.save(null, null, deviceUuid, realPropertyId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{device_uuid}/{realPropertyId}")
    public ResponseEntity<Favorites> deleteByRealPropertyId(
            @PathVariable("device_uuid") String deviceUuid,
            @PathVariable("realPropertyId") Long realPropertyId) {
        favoritesService.deleteByRealPropertyId(null, null, deviceUuid, realPropertyId);
        return ResponseEntity.ok(null);
    }
}
