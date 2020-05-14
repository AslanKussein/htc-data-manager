package kz.dilau.htcdatamanager.config;

public final class Constants {
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String TABLE_NAME_PREFIX = "htc_dm_";
    public static final String DICTIONARY_TABLE_NAME_PREFIX = TABLE_NAME_PREFIX + "dic_";
    public static final String AUTHORIZATION_PREFIX = "Bearer";
    public static final String API_REST_ENDPOINT = "/api";
    public static final String OPEN_API_REST_ENDPOINT = "/open-api";
    public static final String APPLICATIONS_REST_ENDPOINT = "/api/applications";
    public static final String EVENTS_REST_ENDPOINT = "/api/events";
    public static final String CLIENTS_REST_ENDPOINT = "/api/clients";
    public static final String RESIDENTIAL_COMPLEXES_REST_ENDPOINT = "/api/residential-complexes";
    public static final String STATUS_HISTORIES_REST_ENDPOINT = "/api/app-status-histories";
    public static final String NOTES_REST_ENDPOINT = "/api/notes";
    public static final String DICTIONARIES_REST_ENDPOINT = "/open-api/dictionaries";
    public static final String NEW_DICTIONARIES_REST_ENDPOINT = "/open-api/dictionary";
    public static final String MORTGAGE_REST_ENDPOINT = "/api/mortgage";
    public static final String APPLICATIONS_CLIENT_REST_ENDPOINT = "/api/applications-client";
    public static final String REAL_PROPERTY_CLIENT_VIEW_REST_ENDPOINT = "/open-api/real-property-client-view";

    private Constants() {
    }
}
