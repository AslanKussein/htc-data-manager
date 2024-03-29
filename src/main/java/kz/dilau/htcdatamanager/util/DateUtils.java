package kz.dilau.htcdatamanager.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.util.Date;

@UtilityClass
public class DateUtils {
    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
}
