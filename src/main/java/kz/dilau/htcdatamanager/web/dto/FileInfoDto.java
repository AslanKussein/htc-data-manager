package kz.dilau.htcdatamanager.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FileInfoDto", description = "Объект информации файла")
public class FileInfoDto {
    private String uuid;
    private String name;
    private String type;
    private long size;
    @JsonIgnore
    private byte[] data;
}
