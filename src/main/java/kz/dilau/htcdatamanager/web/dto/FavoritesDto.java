package kz.dilau.htcdatamanager.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.domain.Favorites;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@ApiModel(description = "Модель для Ипотека за 3 дня")
@Getter
@Setter
@NoArgsConstructor
public class FavoritesDto {
    private String clientLogin;
    private RealPropertyDto realPropertyDto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createDate;


    public FavoritesDto(Favorites rc) {
        this.clientLogin = rc.getClientLogin();
        this.realPropertyDto = new RealPropertyDto(rc.getRealProperty());
        this.createDate = rc.getCreateDate();
    }
}
