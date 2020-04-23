package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegerPeriod {
    private Integer from;
    private Integer to;

    public Integer getFrom() {
        if (isNull(from)) {
            from = 0;
        }
        return from;
    }

    public Integer getTo() {
        if (isNull(to)) {
            to = 1000000000;
        }
        return to;
    }
}
