package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PropertyOwnerManagerImpl implements PropertyOwnerManager {
    private final RealPropertyOwnerRepository realPropertyOwnerRepository;

    @Override
    public RealPropertyOwner getOwnerById(Long id) {
        return Optional
                .ofNullable(realPropertyOwnerRepository.getOne(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found " + id));
    }

    @Override
    public RealPropertyOwner findOwnerByPhoneNumber(String phoneNumber) {
        return realPropertyOwnerRepository.findByPhoneNumber(phoneNumber).orElseThrow(RuntimeException::new);//todo
    }

    @Override
    public Long saveOwner(RealPropertyOwner owner) {
        return realPropertyOwnerRepository.save(owner).getId();
    }

    @Override
    public void updateOwner(Long id, RealPropertyOwner owner) {

    }

    @Override
    public void deleteOwnerById(Long id) {
        realPropertyOwnerRepository.deleteById(id);
    }
}
