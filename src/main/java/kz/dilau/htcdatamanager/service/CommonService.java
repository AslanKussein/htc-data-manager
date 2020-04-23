package kz.dilau.htcdatamanager.service;

import java.util.List;

public interface CommonService<ID, IN, OUT> {
    OUT getById(final String token, ID id);

    List<OUT> getAll(final String token);

    ID save(final String token, IN input);

    void update(final String token, ID id, IN input);

    void deleteById(final String token, ID id);
}
