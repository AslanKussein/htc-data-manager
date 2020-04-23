package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.service.RealPropertyOwnerService;
import kz.dilau.htcdatamanager.web.dto.RealPropertyOwnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RealPropertyOwnerServiceImpl implements RealPropertyOwnerService {
    private final RealPropertyOwnerRepository ownerRepository;

    @Override
    public RealPropertyOwnerDto findOwnerByPhoneNumber(String phoneNumber) {
        return ownerRepository
                .findByPhoneNumber(phoneNumber)
                .map(RealPropertyOwnerDto::new)
                .orElse(null);
    }

    @Override
    public RealPropertyOwnerDto getById(String token, Long id) {
        RealPropertyOwner owner = getOwnerById(id);
        return new RealPropertyOwnerDto(owner);
    }

    @Override
    public List<RealPropertyOwnerDto> getAll(String token) {
        return Collections.emptyList();
    }

    @Override
    public Long save(String token, RealPropertyOwnerDto dto) {
        return saveOwner(new RealPropertyOwner(), dto);
    }

    public Long saveOwner(RealPropertyOwner owner, RealPropertyOwnerDto dto) {
        owner.setFirstName(dto.getFirstName());
        owner.setSurname(dto.getSurname());
        owner.setPatronymic(dto.getPatronymic());
        owner.setPhoneNumber(dto.getPhoneNumber());
        owner.setEmail(dto.getEmail());
        owner.setGender(dto.getGender());
        return ownerRepository.save(owner).getId();
    }

    @Override
    public void update(String token, Long id, RealPropertyOwnerDto dto) {
        RealPropertyOwner owner = getOwnerById(id);
        saveOwner(owner, dto);
    }

    @Override
    public void deleteById(String token, Long id) {
        RealPropertyOwner owner = getOwnerById(id);
        owner.setRemoved(true);
        ownerRepository.save(owner);
    }

    public RealPropertyOwner getOwnerById(Long id) {
        Optional<RealPropertyOwner> optionalRealPropertyOwner = ownerRepository.findById(id);
        if (optionalRealPropertyOwner.isPresent()) {
            if (optionalRealPropertyOwner.get().isRemoved()) {
                throw EntityRemovedException.createOwnerRemovedById(id);
            }
            return optionalRealPropertyOwner.get();
        } else {
            throw NotFoundException.createOwnerNotFoundById(id);
        }
    }
}
