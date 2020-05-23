package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "credit_programm")
public class CreditProgramm extends BaseEntity<Long> {

    private String nameRu;
    private String nameKz;
    private String nameEn;
    private BigDecimal minDownPayment;
    private BigDecimal maxDownPayment;
    private BigDecimal minCreditPeriod;
    private BigDecimal maxCreditPeriod;
    private BigDecimal percent;
    private Boolean isRemoved = false;


}
