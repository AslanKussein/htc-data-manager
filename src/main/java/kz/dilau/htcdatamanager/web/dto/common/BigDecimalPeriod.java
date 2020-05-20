package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigDecimalPeriod {
    private BigDecimal from;
    private BigDecimal to;

//    public BigDecimal getFrom() {
//        if (isNull(from)) {
//            from = BigDecimal.valueOf(0);
//        }
//        return from;
//    }
//
//    public BigDecimal getTo() {
//        if (isNull(to)) {
//            to = BigDecimal.valueOf(1000000000);
//        }
//        return to;
//    }
}
