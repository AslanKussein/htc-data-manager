package kz.dilau.htcdatamanager.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class KazPostReturnDTO<X> implements Serializable {
    private X streetList;
    private X cityList;
    private X districtList;
}
