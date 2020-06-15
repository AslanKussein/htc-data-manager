package kz.dilau.htcdatamanager.config;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CommissionRange {
    private int from;
    private int to;
    private BigDecimal amount;
}
