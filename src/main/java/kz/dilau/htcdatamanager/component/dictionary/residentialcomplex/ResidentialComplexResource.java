package kz.dilau.htcdatamanager.component.dictionary.residentialcomplex;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/residential-complexes")
public class ResidentialComplexResource {
    private final ResidentialComplexManager residentialComplexManager;
}
