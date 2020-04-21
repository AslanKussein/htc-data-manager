package kz.dilau.htcdatamanager.component.utils;

import kz.dilau.htcdatamanager.component.common.dto.BigDecimalPeriod;
import kz.dilau.htcdatamanager.component.common.dto.IntegerPeriod;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PeriodUtils {
    public static IntegerPeriod mapToIntegerPeriod(Integer from, Integer to) {
        return new IntegerPeriod(from, to);
    }
    public static BigDecimalPeriod mapToBigDecimalPeriod(BigDecimal from, BigDecimal to) {
        return new BigDecimalPeriod(from, to);
    }
}
