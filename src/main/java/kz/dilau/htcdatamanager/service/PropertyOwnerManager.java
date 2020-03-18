package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyOwnerManager {
    private final RealPropertyOwnerRepository realPropertyOwnerRepository;

    @Autowired
    public PropertyOwnerManager(RealPropertyOwnerRepository realPropertyOwnerRepository) {
        this.realPropertyOwnerRepository = realPropertyOwnerRepository;
    }

    public List<RealPropertyOwner> getAll() {
        return realPropertyOwnerRepository.findAll();
    }

    public RealPropertyOwner getById(Long id) {
        return realPropertyOwnerRepository.getOne(id);
    }

    public void deleteById(Long id) {
        realPropertyOwnerRepository.deleteById(id);
    }

    public void update(Long id, RealPropertyOwner var0) {
        RealPropertyOwner var1 = realPropertyOwnerRepository.getOne(id);
        BeanUtils.copyProperties(var0, var1);
        realPropertyOwnerRepository.save(var1);
    }

    public void save(RealPropertyOwner realProperty) {
        realPropertyOwnerRepository.save(realProperty);
    }
}
