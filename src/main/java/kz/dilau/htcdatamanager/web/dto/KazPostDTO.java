package kz.dilau.htcdatamanager.web.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class KazPostDTO implements Serializable {
    private String postcode;
    private List<Parts> parts;
    @Data
    public static class Type {
        private String id;
        private String nameRus;
        private String nameKaz;
        private String nameLat;
    }
    @Data
    public class Parts {
        private String id;
        private String nameKaz;
        private String nameRus;
        private Type type;
        private String parentId;
        private int actual;
    }
}
