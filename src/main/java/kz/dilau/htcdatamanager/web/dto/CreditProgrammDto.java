package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.CreditProgramm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@ApiModel(description = "Справочник Программ кредитования")
@Getter
@Setter
@NoArgsConstructor
public class CreditProgrammDto {
    @ApiModelProperty(value = "ID")
    @Nullable
    private Long id;
    @ApiModelProperty(value = "Наименование Ru")
    private String nameRu;
    @ApiModelProperty(value = "Наименование Kz")
    private String nameKz;
    @ApiModelProperty(value = "Наименование En")
    private String nameEn;
    @ApiModelProperty(value = "Минимальный первоначальный взнос")
    private BigDecimal minDownPayment;
    @ApiModelProperty(value = "Максимальный первоначальный взнос")
    private BigDecimal maxDownPayment;
    @ApiModelProperty(value = "Минимальный срок (год)")
    private BigDecimal minCreditPeriod;
    @ApiModelProperty(value = "Максимальный срок (год)")
    private BigDecimal maxCreditPeriod;
    @ApiModelProperty(value = "Ставка вознаграждения (%)")
    private BigDecimal percent;

    public CreditProgrammDto(CreditProgramm rc) {
        this.id = rc.getId();
        this.nameRu = rc.getNameRu();
        this.nameKz = rc.getNameKz();
        this.nameEn = rc.getNameEn();
        this.maxCreditPeriod = rc.getMaxCreditPeriod();
        this.minCreditPeriod = rc.getMinCreditPeriod();
        this.maxDownPayment = rc.getMaxDownPayment();
        this.minDownPayment = rc.getMinDownPayment();
        this.percent = rc.getPercent();
    }
}
