package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import kz.dilau.htcdatamanager.repository.dictionary.PossibleReasonForBiddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/possibleReasonForBidding")
public class PossibleReasonForBiddingResource {
    private final PossibleReasonForBiddingRepository repository;

    @GetMapping("")
    public ResponseEntity<List<PossibleReasonForBidding>> getAllPossibleReasonsForBidding() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PossibleReasonForBidding> getPossibleReasonForBiddingById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> savePossibleReasonForBidding(@RequestBody PossibleReasonForBidding parkingType) {
        return ResponseEntity.ok(repository.save(parkingType).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePossibleReasonForBiddingById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updatePossibleReasonForBidding(@RequestBody PossibleReasonForBidding parkingType) {
        repository.save(parkingType);
        return ResponseEntity.noContent().build();
    }
}
