package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Mortgage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Модель для Ипотека за 3 дня")
@Getter
@Setter
@NoArgsConstructor
public class MortgageDto {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "Логин")
    private String login;
    @ApiModelProperty(value = "Сумма кредита, тг")
    private Long creditSum;
    @ApiModelProperty(value = "Срок кредита, мес")
    private Long creditTerm;
    @ApiModelProperty(value = "Общий доход, тг")
    private Long totalIncome;
    @ApiModelProperty(value = "Действующие кредиты")
    private Boolean activeCredit;
    @ApiModelProperty(value = "Платеж по действующим займам, тг/мес")
    private Long activeCreditSum;



    public MortgageDto(Mortgage rc) {
        this.id = rc.getId();
        this.login = rc.getLogin();
        this.creditSum = rc.getCreditSum();
        this.creditTerm = rc.getCreditTerm();
        this.totalIncome = rc.getTotalIncome();
        this.activeCredit = rc.getActiveCredit();
        this.activeCreditSum = rc.getActiveCreditSum();
    }
}
