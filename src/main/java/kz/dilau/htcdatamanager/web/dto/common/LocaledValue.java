package kz.dilau.htcdatamanager.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocaledValue {
    private String ru;
    private String kk;
    private String en;

    public LocaledValue(String ru) {
        this.ru = ru;
        this.kk = ru;
        this.en = ru;
    }
}
