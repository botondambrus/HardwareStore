package edu.bbte.idde.abim2109.spring.repository.dao.sqldao;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.repository.dao.HardwareStoreDao;
import edu.bbte.idde.abim2109.spring.exception.RepositoryException;
import edu.bbte.idde.abim2109.spring.repository.dao.database.SqlDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Profile("prod")
public class HardwareStoreSqlDao implements HardwareStoreDao {
    private static final Logger logger = LoggerFactory.getLogger(HardwareStoreSqlDao.class);

    @Autowired
    private SqlDatabase database;

    @Override
    public HardwareStore findById(Long id) {
        logger.info("Reading item with ID {}\n", id);
        String query = "SELECT * FROM hardware_store WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToHardwareStore(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read item");
        }

        return null;
    }

    @Override
    public HardwareStore create(HardwareStore entity) {
        String query =
                "INSERT INTO hardware_store (name, category, description, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getPrice());
            statement.setInt(5, entity.getQuantity());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long newId = generatedKeys.getLong(1);
                        entity.setId(newId);
                    }
                }
                logger.info("Item with ID {} has been created.\n", entity.getId());
                return entity;
            }

            logger.warn("Failed to create item.\n");
            throw new RepositoryException("Failed to create item");

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to create item");
        }
    }

    @Override
    public HardwareStore update(Long id, HardwareStore entity) {
        String query = "UPDATE hardware_store"
                + " SET name = ?, category = ?, description = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getPrice());
            statement.setInt(5, entity.getQuantity());
            statement.setLong(6, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Item with ID {} has been updated.\n", id);
                return entity;
            }
            logger.warn("Failed to update item with ID {}\n", id);
            throw new RepositoryException("Failed to update item");

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to update item");
        }

    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM hardware_store WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Item with ID {} has been deleted.\n", id);

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to delete item");
        }
    }

    @Override
    public Collection<HardwareStore> findAll() {
        Collection<HardwareStore> hardwareStores = new ArrayList<>();
        String query = "SELECT * FROM hardware_store";
        logger.info("Reading all items.\n");

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                HardwareStore hardwareStore = mapResultSetToHardwareStore(resultSet);
                hardwareStores.add(hardwareStore);
            }

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read all items");
        }

        return hardwareStores;
    }

    @Override
    public HardwareStore findByName(String name) {
        logger.info("Reading item with name {}\n", name);
        String query = "SELECT * FROM hardware_store WHERE name = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToHardwareStore(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read item");
        }

        return null;
    }

    private HardwareStore mapResultSetToHardwareStore(ResultSet resultSet) throws SQLException {
        HardwareStore hardwareStore = new HardwareStore();
        hardwareStore.setId(resultSet.getLong("id"));
        hardwareStore.setName(resultSet.getString("name"));
        hardwareStore.setCategory(resultSet.getString("category"));
        hardwareStore.setDescription(resultSet.getString("description"));
        hardwareStore.setPrice(resultSet.getInt("price"));
        hardwareStore.setQuantity(resultSet.getInt("quantity"));
        return hardwareStore;
    }
}
