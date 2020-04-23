package kz.dilau.htcdatamanager.util;

import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@UtilityClass
public class StringUtils {
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    public static String stringToUppercaseLike(String text) {
        if (nonNull(text)) {
            return "%" + text.trim().toUpperCase() + "%";
        }
        return null;
    }

    public static String mapFullName(String name, String surname, String middlename) {
        return (isNull(name) ? EMPTY_STRING : name) +
                (isNull(surname) ? EMPTY_STRING : SPACE + surname) +
                (isNull(middlename) ? EMPTY_STRING : SPACE + middlename);
    }
}
