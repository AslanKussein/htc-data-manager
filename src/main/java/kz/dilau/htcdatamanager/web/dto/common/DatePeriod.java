package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.isNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatePeriod {
    private Date from;
    private Date to;

    public Date getFrom() {
        if (isNull(from)) {
            try {
                from = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1900");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return from;
    }

    public Date getTo() {
        if (isNull(to)) {
            try {
                to = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.4000");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return to;
    }
}
