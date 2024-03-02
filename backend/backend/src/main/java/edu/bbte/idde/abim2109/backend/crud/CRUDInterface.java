package edu.bbte.idde.abim2109.backend.crud;

import java.util.List;

public interface CRUDInterface<T> {
    T create(T item);



    T read(Integer id);

    List<T> readAll();

    T update(Integer id, T updatedItem);

    void delete(Integer id);
}