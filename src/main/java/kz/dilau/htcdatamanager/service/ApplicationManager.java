package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationManager {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationManager(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public Application getById(Long id) {
        return applicationRepository.getOne(id);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public void update(Long id, Application var0) {
        Application var1 = applicationRepository.getOne(id);
        BeanUtils.copyProperties(var0, var1);
        applicationRepository.save(var1);
    }

    public void save(Application application) {
        applicationRepository.save(application);
    }
}
