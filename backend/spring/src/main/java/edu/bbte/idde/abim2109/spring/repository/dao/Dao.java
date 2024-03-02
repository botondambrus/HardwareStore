package edu.bbte.idde.abim2109.spring.repository.dao;

import edu.bbte.idde.abim2109.spring.model.BaseEntity;
import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T findById(Long id);

    T create(T entity);

    T update(Long id, T entity);

    void delete(Long id);

    Collection<T> findAll();
}
