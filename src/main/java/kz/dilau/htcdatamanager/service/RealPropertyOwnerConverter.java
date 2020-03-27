package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealPropertyOwnerConverter {
    private final RealPropertyOwnerRepository ownerRepository;

    @Autowired
    public RealPropertyOwnerConverter(RealPropertyOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public RealPropertyOwner toSell(final ApplicationDto dto) {
        final RealPropertyOwner owner;
        if (dto.getClientId() != null) {
            owner = ownerRepository.getOne(dto.getClientId());
        } else {
            owner = new RealPropertyOwner();
            owner.setFirstName(dto.getFirstName());
            owner.setSurname(dto.getSurname());
            owner.setPatronymic(dto.getPatronymic());
            owner.setPhoneNumber(dto.getPhoneNumber());
            owner.setEmail(dto.getEmail());
            owner.setGender(dto.getGender());
        }
        return owner;
    }
}
