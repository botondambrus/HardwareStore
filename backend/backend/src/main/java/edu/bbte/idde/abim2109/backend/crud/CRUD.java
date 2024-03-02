package edu.bbte.idde.abim2109.backend.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import edu.bbte.idde.abim2109.backend.model.EnityID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRUD<T extends EnityID> implements CRUDInterface<T> {

    private static final Logger logger = LoggerFactory.getLogger(CRUD.class);
    private final ConcurrentHashMap<Integer, T> storage = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    @Override
    public T create(T item) {
        Integer currentId = id.incrementAndGet();
        item.setId(currentId);

        storage.put(currentId, item);
        T createdItem = storage.get(currentId);

        boolean created = createdItem != null;

        if (created) {
            logger.info("Item with ID {} has been created.\n", currentId);
        } else {
            logger.warn("Failed to create item with ID {}\n", currentId);
        }
        return createdItem;
    }

    @Override
    public T read(Integer id) {
        logger.info("Reading item with ID {}\n", id);
        return storage.get(id);
    }

    @Override
    public List<T> readAll() {
        logger.info("Reading all items.\n");
        return new ArrayList<>(storage.values());
    }

    @Override
    public T update(Integer id, T updatedItem) {
        T existingItem = storage.get(id);
        if (existingItem == null) {
            logger.warn("Item with ID {} does not exist.\n", id);
            return null;
        }

        updatedItem.setId(id);
        storage.put(id, updatedItem);
        T updateItem = storage.get(id);
        boolean updated = updateItem != null;

        if (updated) {
            logger.info("Item with ID {} has been updated.\n", id);

        } else {
            logger.warn("Failed to update item with ID {}\n", id);
        }

        return updatedItem;
    }

    @Override
    public void delete(Integer id) {
        boolean deleted = storage.remove(id) != null;

        if (deleted) {
            logger.info("Item with ID {} has been deleted.\n", id);
        } else {
            logger.warn("Failed to delete item with ID {}\n", id);
        }
    }
}