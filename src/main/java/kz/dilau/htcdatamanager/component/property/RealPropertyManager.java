package kz.dilau.htcdatamanager.component.property;

import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealPropertyManager {
    private final RealPropertyRepository realPropertyRepository;

    @Autowired
    public RealPropertyManager(RealPropertyRepository realPropertyRepository) {
        this.realPropertyRepository = realPropertyRepository;
    }

    public RealProperty getById(Long id) {
        return realPropertyRepository.getOne(id);
    }

    public List<RealProperty> getAll() {
        return realPropertyRepository.findAll();
    }

    public void deleteById(Long id) {
        realPropertyRepository.deleteById(id);
    }

    public void update(Long id, RealProperty var0) {
        RealProperty var1 = realPropertyRepository.getOne(id);
        BeanUtils.copyProperties(var0, var1);
        realPropertyRepository.save(var1);
    }

    public void save(RealProperty realProperty) {
        realPropertyRepository.save(realProperty);
    }
}
