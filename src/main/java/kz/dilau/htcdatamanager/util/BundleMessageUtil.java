package kz.dilau.htcdatamanager.util;

import kz.dilau.htcdatamanager.web.dto.common.LocaledValue;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundleMessageUtil {
    private BundleMessageUtil() {
    }

    public static LocaledValue getLocaledValue(String key, Object... params) {
        LocaledValue localedValue = new LocaledValue();
        localedValue.setRu(getMessage(key, new Locale("ru"), params));
        localedValue.setKk(getMessage(key, new Locale("kk"), params));
        localedValue.setEn(getMessage(key, new Locale("en"), params));
        return localedValue;
    }

    public static String getMessage(String key, Locale locale, Object... params) {
        return MessageFormat.format(ResourceBundle.getBundle("messages", locale).getString(key), params);
    }
}
