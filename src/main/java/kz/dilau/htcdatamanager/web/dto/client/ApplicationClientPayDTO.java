package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Модель для заполнения информаций об оплате", description = "Модель для заполнения информаций об оплате")
public class ApplicationClientPayDTO {
    private Long payTypeId;
    private BigDecimal payedSum;
    private boolean isPayed;
}
