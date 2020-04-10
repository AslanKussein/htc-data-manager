package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.service.ApplicationConverterManager;
import lombok.Getter;

@Getter
public enum ApplicationType implements ApplicationConverterManager {
    SHORT_FORM {
        @Override
        public String getConverterName() {
            return "shortFormConverter";
        }
    },
    FULL_FORM_FOR_PURCHASE {
        @Override
        public String getConverterName() {
            return "fullFormForPurchaseConverter";
        }
    },
    FULL_FORM_FOR_SELL {
        @Override
        public String getConverterName() {
            return "fullFormForSellConverter";
        }
    }
}
