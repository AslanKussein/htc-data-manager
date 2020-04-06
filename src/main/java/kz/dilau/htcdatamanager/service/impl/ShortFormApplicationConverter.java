package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.service.ApplicationConverter;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service("shortFormConverter")
public class ShortFormApplicationConverter implements ApplicationConverter {
    private final EntityManager entityManager;
    private final RealPropertyOwnerRepository ownerRepository;

    @Override
    public Application convertFromDto(ApplicationDto dto) {
        final Application application;
        final RealPropertyOwner owner;
        final OperationType operationType;
        final Application.ApplicationBuilder builder = Application.builder();
        if (dto.getOperationTypeId() != null) {
            operationType = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
            builder.operationType(operationType);
        }
        if (dto.getClientId() != null && dto.getClientId() > 0L) {
            owner = ownerRepository.getOne(dto.getClientId());
        } else {
            owner = RealPropertyOwner.builder()
                    .phoneNumber(dto.getPhoneNumber())
                    .firstName(dto.getFirstName())
                    .surname(dto.getSurname())
                    .patronymic(dto.getPatronymic())
                    .build();
            ownerRepository.save(owner);//todo надо ли сохранять? пока хз
        }
        application = builder
                .owner(owner)
                .note(dto.getNote())
                .build();
        return application;
    }
}
