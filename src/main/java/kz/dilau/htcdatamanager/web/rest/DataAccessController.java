package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.web.dto.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.service.DataAccessManager;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/data-accesses")
public class DataAccessController {
    private final DataAccessManager dataAccessManager;

    @GetMapping
    public ListResponse<CheckOperationGroupDto> func0(@RequestHeader(AUTHORIZATION) final String token) {
        return dataAccessManager.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
    }

//    public ApplicationDto getApplicationById(final String token, Long id) {
//        ApplicationDto dto = new ApplicationDto();
//        ListResponse<CheckOperationGroupDto> checkOperationList = dataAccessManager.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
//        Application application = applicationRepository.getOne(id);
//        checkOperationList
//                .getData()
//                .stream()
//                .filter(e -> "APPLICATION_GROUP".equals(e.getCode()))
//                .findFirst()
//                .ifPresent(e -> {
//                    List<String> operations = e.getOperations();
//                    for (String oper : operations) {
//                        switch (oper) {
//                            case "VIEW_SALE_DEAL_INFO":
//                                dto.setId(application.getId());
//                                dto.setOperationTypeId(application.getOperationType().getId());
////                                dto.setObjectTypeId(application.getObjectType().getId());
////                                dto.setObjectPrice(application.getObjectPrice());
//                                dto.setMortgage(application.getMortgage());
//                                dto.setEncumbrance(application.getEncumbrance());
//                                dto.setSharedOwnershipProperty(application.getSharedOwnershipProperty());
//                                dto.setExchange(application.getExchange());
//                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
////                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
//                                dto.setTheSizeOfTrades(application.getTheSizeOfTrades());
//                                break;
//                            case "NOT_ACCESS_ VIEW_SALE_DEAL_INFO":
//                                break;
//                            case "VIEW_PURCHASE_DEAL_INFO":
//                                dto.setOperationTypeId(application.getOperationType().getId());
////                                dto.setObjectTypeId(application.getObjectType().getId());
////                                dto.setObjectPriceFrom(application.getObjectPriceFrom());
////                                dto.setObjectPriceTo(application.getObjectPriceTo());
//                                dto.setMortgage(application.getMortgage());
//                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
////                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
//                                break;
//                            case "NOT_ACCESS_ VIEW_PURCHASE_DEAL_INFO":
//                                break;
//                            case "VIEW_DEAL_DATA":
//                                dto.setContractPeriod(application.getContractPeriod());
////                                dto.setAmount(application.getAmount());//todo
//                                dto.setCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice());
//                                break;
//                        }
//                    }
//                });
//        return dto;
//    }
}
