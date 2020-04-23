package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void addFilesToProperty(Long propertyId, List<String> photoIds, List<String> housingPlans, List<String> virtualTours) {
        RealProperty realProperty = realPropertyRepository.getOne(propertyId);
        Map<RealPropertyFileType, Set<String>> filesMap = realProperty.getFilesMap();
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.PHOTO)) {
                filesMap.get(RealPropertyFileType.PHOTO).addAll(photoIds);
            } else {
                filesMap.put(RealPropertyFileType.PHOTO, new HashSet<>(photoIds));
            }
        }
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.HOUSING_PLAN)) {
                filesMap.get(RealPropertyFileType.HOUSING_PLAN).addAll(housingPlans);
            } else {
                filesMap.put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(housingPlans));
            }
        }
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.VIRTUAL_TOUR)) {
                filesMap.get(RealPropertyFileType.VIRTUAL_TOUR).addAll(virtualTours);
            } else {
                filesMap.put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(virtualTours));
            }
        }
        realPropertyRepository.save(realProperty);
    }
}
