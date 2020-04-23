package kz.dilau.htcdatamanager.config;

public final class Constants {
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String TABLE_NAME_PREFIX = "htc_dm_";
    public static final String DICTIONARY_TABLE_NAME_PREFIX = TABLE_NAME_PREFIX + "dic_";
    public static final String AUTHORIZATION_PREFIX = "Bearer";
    public static final String APPLICATIONS_REST_ENDPOINT = "/applications";
    public static final String EVENTS_REST_ENDPOINT = "/events";
    public static final String OWNERS_REST_ENDPOINT = "/property-owners";
    public static final String RESIDENTIAL_COMPLEXES_REST_ENDPOINT = "/residential-complexes";

    private Constants() {
    }
}
