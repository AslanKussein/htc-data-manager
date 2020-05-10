package kz.dilau.htcdatamanager.service.dictionary;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

public enum Dictionary implements AbstractDictionary {
    APPLICATION_STATUSES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "application_status";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.SYSTEM;
        }
    },
    OBJECT_TYPES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "object_type";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.SYSTEM;
        }
    },
    OPERATION_TYPES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "operation_type";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.SYSTEM;
        }
    },
    YES_NO {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "yes_no";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.SYSTEM;
        }
    },

    CITIES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "city";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    COUNTRIES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "country";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    DISTRICTS {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "district";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    MATERIALS_OF_CONSTRUCTION {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "material_of_construction";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    PARKING_TYPES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "parking_type";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    POSSIBLE_REASONS_FOR_BIDDING {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "possible_reason_for_bidding";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    PROPERTY_DEVELOPERS {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "property_developer";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    STREETS {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "street";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    YARD_TYPES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "yard_type";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    TYPE_OF_ELEVATOR {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "type_of_elevator";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    SEWERAGE_SYSTEMS {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "sewerage";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    HEATING_SYSTEMS {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "heating_system";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    },
    EVENT_TYPES {
        @Override
        public String getTableName() {
            return DICTIONARY_TABLE_NAME_PREFIX + "event_type";
        }

        @Override
        public DictionaryType getType() {
            return DictionaryType.CUSTOM;
        }
    }
}
