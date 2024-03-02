package edu.bbte.idde.abim2109.spring.repository.dao.memdao;

import edu.bbte.idde.abim2109.spring.model.BaseEntity;
import edu.bbte.idde.abim2109.spring.repository.dao.Dao;
import edu.bbte.idde.abim2109.spring.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AbstractMemDao<T extends BaseEntity> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractMemDao.class);
    protected final Map<Long, T> storage = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public T findById(Long id) {
        logger.info("Reading item with ID {}\n", id);
        return storage.get(id);
    }

    @Override
    public T create(T entity) {
        Long currentId = id.incrementAndGet();
        entity.setId(currentId);

        storage.put(currentId, entity);
        T createdItem = storage.get(currentId);

        boolean created = createdItem != null;

        if (created) {
            logger.info("Item with ID {} has been created.\n", currentId);
        } else {
            logger.warn("Failed to create item with ID {}\n", currentId);
            throw new RepositoryException("Failed to create item");
        }
        return createdItem;
    }

    @Override
    public T update(Long id, T entity) {
        T existingItem = storage.get(id);
        if (existingItem == null) {
            logger.warn("Item with ID {} does not exist.\n", id);
            return null;
        }

        entity.setId(id);
        storage.put(id, entity);
        T updateItem = storage.get(id);
        boolean updated = updateItem != null;

        if (updated) {
            logger.info("Item with ID {} has been updated.\n", id);

        } else {
            logger.warn("Failed to update item with ID {}\n", id);
            throw new RepositoryException("Failed to update item");
        }

        return entity;
    }

    @Override
    public void delete(Long id) {
        boolean deleted = storage.remove(id) != null;

        if (deleted) {
            logger.info("Item with ID {} has been deleted.\n", id);
        } else {
            logger.warn("Failed to delete item with ID {}\n", id);
            throw new RepositoryException("Failed to delete item");
        }
    }

    @Override
    public Collection<T> findAll() {
        logger.info("Reading all items.\n");
        return storage.values();
    }
}
