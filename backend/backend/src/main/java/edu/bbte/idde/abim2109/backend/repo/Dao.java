package edu.bbte.idde.abim2109.backend.repo;

import edu.bbte.idde.abim2109.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T findById(Integer id);

    T create(T entity);

    T update(Integer id, T entity);

    void delete(Integer id);

    Collection<T> findAll();
}
