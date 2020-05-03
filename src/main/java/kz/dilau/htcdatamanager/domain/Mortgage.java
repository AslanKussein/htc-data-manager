package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "MORTGAGE")
public class Mortgage extends BaseEntity<Long> {
    @Column(name = "login")
    private String login;
    @Column(name = "credit_sum")
    private Long creditSum;
    @Column(name = "credit_term")
    private Long creditTerm;
    @Column(name = "total_income")
    private Long totalIncome;
    @Column(name = "active_credit")
    private Boolean activeCredit;
    @Column(name = "active_credit_sum")
    private Long activeCreditSum;
    @Column(name = "visit_date")
    private ZonedDateTime visitDate;
    @Column(name = "is_removed")
    private Boolean isRemoved = false;
}
