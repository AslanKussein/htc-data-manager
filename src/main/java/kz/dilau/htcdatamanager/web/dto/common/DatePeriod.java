package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatePeriod {
    private ZonedDateTime from;
    private ZonedDateTime to;

    public ZonedDateTime getFrom() {
        if (isNull(from)) {
            try {
                from = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1900").toInstant().atZone(ZoneId.systemDefault());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return from;
    }

    public ZonedDateTime getTo() {
        if (isNull(to)) {
            try {
                to = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.4000").toInstant().atZone(ZoneId.systemDefault());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return to;
    }
}
