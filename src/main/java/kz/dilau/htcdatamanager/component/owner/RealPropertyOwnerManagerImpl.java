package kz.dilau.htcdatamanager.component.owner;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RealPropertyOwnerManagerImpl implements RealPropertyOwnerManager {
    private final RealPropertyOwnerRepository rpoRepository;

    @Override
    public RealPropertyOwnerDto findOwnerByPhoneNumber(String phoneNumber) {
        return rpoRepository
                .findByPhoneNumber(phoneNumber)
                .map(RealPropertyOwnerDto::new)
                .orElse(null);
    }

    @Override
    public RealPropertyOwnerDto getById(String token, Long id) {
        RealPropertyOwner owner = rpoRepository
                .findById(id)
                .orElseThrow(() -> new RealPropertyOwnerNotFoundException(id));
        return new RealPropertyOwnerDto(owner);
    }

    @Override
    public List<RealPropertyOwnerDto> getAll(String token) {
        return Collections.emptyList();
    }

    @Override
    public Long save(String token, RealPropertyOwnerDto dto) {
        RealPropertyOwner owner = RealPropertyOwner.builder()
                .firstName(dto.getFirstName())
                .surname(dto.getSurname())
                .patronymic(dto.getPatronymic())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .build();
        Long id = rpoRepository.save(owner).getId();
        return id;
    }

    @Override
    public void update(String token, Long id, RealPropertyOwnerDto dto) {
        RealPropertyOwner owner = rpoRepository
                .findById(id)
                .orElseThrow(() -> new RealPropertyOwnerNotFoundException(id));
        owner.setFirstName(dto.getFirstName());
        owner.setSurname(dto.getSurname());
        owner.setPatronymic(dto.getPatronymic());
        owner.setPhoneNumber(dto.getPhoneNumber());
        owner.setEmail(dto.getEmail());
        owner.setGender(dto.getGender());
        rpoRepository.save(owner);
    }

    @Override
    public void deleteById(String token, Long id) {
        boolean exists = rpoRepository.existsById(id);
        if (!exists) throw new RealPropertyOwnerNotFoundException(id);
        rpoRepository.deleteById(id);
    }
}
