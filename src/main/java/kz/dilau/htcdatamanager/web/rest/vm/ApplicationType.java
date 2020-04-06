package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.service.ApplicationConverterManager;
import lombok.Getter;

@Getter
public enum ApplicationType implements ApplicationConverterManager {
    SHORT_FORM("shortForm") {
        @Override
        public String getConverterName() {
            return "shortFormConverter";
        }
    },
    FULL_FORM_FOR_PURCHASE("fullFormForPurchase") {
        @Override
        public String getConverterName() {
            return "fullFormForPurchaseConverter";
        }
    },
    FULL_FORM_FOR_SELL("fullFormForSell") {
        @Override
        public String getConverterName() {
            return "fullFormForSellConverter";
        }
    };

    private final String name;

    ApplicationType(String name) {
        this.name = name;
    }
}
