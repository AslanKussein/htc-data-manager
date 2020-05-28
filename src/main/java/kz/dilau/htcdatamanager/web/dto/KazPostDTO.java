package kz.dilau.htcdatamanager.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class KazPostDTO implements Serializable {
    private String id;
    private String postcode;
    private String addressRus;
    private String addressKaz;
    private FullAddress fullAddress;

    @Data
    public static class FullAddress {
        private String postcode;
        private String rka;
        private Type type;
        private List<Parts> parts;
        private String number;
    }

    @Data
    public static class Type {
        private String id;
        private String nameRus;
        private String nameKaz;
        private String nameLat;
    }
    @Data
    public static class Parts {
        private String id;
        private String nameKaz;
        private String nameRus;
        private Type type;
        private String parentId;
        private int actual;
    }
}
