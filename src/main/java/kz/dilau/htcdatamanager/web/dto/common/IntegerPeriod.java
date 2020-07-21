package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegerPeriod {
    private Integer from;
    private Integer to;
}
