package kz.dilau.htcdatamanager.web.dto;

import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class KazPostReturnDTO implements Serializable {
    private DictionaryDto street;
    private DictionaryDto city;
    private DictionaryDto district;
    private Integer houseNumber;
}
