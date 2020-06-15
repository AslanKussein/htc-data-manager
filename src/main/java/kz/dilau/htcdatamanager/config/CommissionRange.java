package kz.dilau.htcdatamanager.config;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CommissionRange {
    private long from;
    private long to;
    private BigDecimal amount;
}
