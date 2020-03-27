package kz.dilau.htcdatamanager.web.rest.vm;

import lombok.Getter;

@Getter
public enum ApplicationType {
    SHORT_FORM("short"),
    BUY("buy"),
    SELL("sell");

    private final String name;

    ApplicationType(String name) {
        this.name = name;
    }
}
