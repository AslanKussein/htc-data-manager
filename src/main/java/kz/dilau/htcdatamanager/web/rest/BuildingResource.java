package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.domain.Building;
import kz.dilau.htcdatamanager.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.BUILDING_REST_ENDPOINT)
public class BuildingResource {
    private final BuildingService buildingService;

    @GetMapping("/{postcode}")
    public ResponseEntity<Building> getById(@PathVariable("postcode") String postcode) {
        Building result = buildingService.getByPostcode(postcode);
        return ResponseEntity.ok(result);
    }

}
