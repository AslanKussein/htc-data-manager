package kz.dilau.htcdatamanager.web.dto.user;

import kz.dilau.htcdatamanager.web.dto.OrganizationDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDataDto {
    private BigDecimal rating;
    private BigDecimal agentContractAmount;
    private BigDecimal depositAmount;
    private BigDecimal saleAmount;

    private Long organizationId;
    private OrganizationDto organizationDto;
    private Long group;
}
