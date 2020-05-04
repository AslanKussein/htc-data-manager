package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class EntityServiceImpl implements EntityService {
    private final EntityManager entityManager;

    @Override
    public <T> T mapRequiredEntity(Class<T> clazz, Long id) {
        T dict = mapEntity(clazz, id);
        if (isNull(dict)) {
            throw BadRequestException.createRequiredIsEmpty(clazz.getName());
        }
        return dict;
    }

    @Override
    public <T> T mapEntity(Class<T> clazz, Long id) {
        if (nonNull(id) && id != 0L) {
            T dict = entityManager.find(clazz, id);
            if (isNull(dict)) {
                throw NotFoundException.createEntityNotFoundById(clazz.getName(), id);
            }
            return dict;
        }
        return null;
    }
}
