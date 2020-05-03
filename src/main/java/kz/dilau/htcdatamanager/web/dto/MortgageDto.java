package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.dictionary.Mortgage;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApiModel(description = "Модель для справочника Жилой комплекс")
@Getter
@Setter
@NoArgsConstructor
public class MortgageDto {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "ФИО")
    private String fio;
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
        this.fio = rc.getFio();
        this.creditSum = rc.getCreditSum();
        this.creditTerm = rc.getCreditTerm();
        this.totalIncome = rc.getTotalIncome();
        this.activeCredit = rc.getActiveCredit();
        this.activeCreditSum = rc.getActiveCreditSum();
    }
}
