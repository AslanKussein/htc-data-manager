package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.domain.dictionary.PossibleReasonForBidding;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class ApplicationConverter {
    private final ApplicationRepository applicationRepository;
    private final EntityManager entityManager;

    @Autowired
    public ApplicationConverter(ApplicationRepository applicationRepository, EntityManager entityManager) {
        this.applicationRepository = applicationRepository;
        this.entityManager = entityManager;
    }

    public Application toSell(final ApplicationDto dto) {
        final Application application = new Application();
        if (dto.getOperationTypeId() != null) {
            final OperationType reference = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
            application.setOperationType(reference);
        }
        if (dto.getObjectTypeId() != null) {
            final ObjectType reference = entityManager.getReference(ObjectType.class, dto.getObjectTypeId());
            application.setObjectType(reference);
        }
        if (dto.getPossibleReasonForBiddingId() != null) {
            PossibleReasonForBidding reference = entityManager.getReference(PossibleReasonForBidding.class, dto.getPossibleReasonForBiddingId());
            application.setPossibleReasonForBidding(reference);
        }
        application.setObjectPrice(dto.getObjectPrice());
        application.setMortgage(dto.getMortgage());
        application.setEncumbrance(dto.getEncumbrance());
        application.setSharedOwnershipProperty(dto.getSharedOwnershipProperty());
        application.setExchange(dto.getExchange());
        application.setProbabilityOfBidding(dto.getProbabilityOfBidding());
        application.setTheSizeOfTrades(dto.getTheSizeOfTrades());
        application.setContractPeriod(dto.getContractPeriod());
        application.setAmount(dto.getAmount());
        application.setCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice());
        application.setNote(dto.getNote());
        return application;
    }

    public Application toBuy(final ApplicationDto dto) {
        final Application application = new Application();
        if (dto.getOperationTypeId() != null) {
            final OperationType reference = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
            application.setOperationType(reference);
        }
        if (dto.getObjectTypeId() != null) {
            final ObjectType reference = entityManager.getReference(ObjectType.class, dto.getObjectTypeId());
            application.setObjectType(reference);
        }
        if (dto.getPossibleReasonForBiddingId() != null) {
            PossibleReasonForBidding reference = entityManager.getReference(PossibleReasonForBidding.class, dto.getPossibleReasonForBiddingId());
            application.setPossibleReasonForBidding(reference);
        }
        application.setObjectPriceFrom(dto.getObjectPriceFrom());
        application.setObjectPriceTo(dto.getObjectPriceTo());
        application.setMortgage(dto.getMortgage());
        application.setProbabilityOfBidding(dto.getProbabilityOfBidding());
        application.setContractPeriod(dto.getContractPeriod());
        application.setAmount(dto.getAmount());
        application.setCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice());
        application.setNote(dto.getNote());
        return application;
    }
}
