package edu.bbte.idde.abim2109.backend.repo.sqldao;

import edu.bbte.idde.abim2109.backend.model.Purchase;
import edu.bbte.idde.abim2109.backend.repo.PurchaseDao;
import edu.bbte.idde.abim2109.backend.repo.RepositoryException;
import edu.bbte.idde.abim2109.backend.repo.database.SqlDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PurchaseSqlDao implements PurchaseDao {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseSqlDao.class);

    private final SqlDatabase database;

    public PurchaseSqlDao(SqlDatabase database) {
        this.database = database;
    }

    @Override
    public Purchase findById(Integer id) {
        logger.info("Reading item with ID {}\n", id);
        String query = "SELECT * FROM purchase WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPurchase(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read item");
        }

        return null;
    }

    @Override
    public Purchase create(Purchase entity) {
        String query = "INSERT INTO purchase (name, hardwareId, quantity, address, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getHardwareId());
            statement.setInt(3, entity.getQuantity());
            statement.setString(4, entity.getAddress());
            statement.setString(5, entity.getEmail());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
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
    public Purchase update(Integer id, Purchase entity) {
        String query =
                "UPDATE purchase SET name = ?, hardwareId = ?, quantity = ?, address = ?, email = ? WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getHardwareId());
            statement.setInt(3, entity.getQuantity());
            statement.setString(4, entity.getAddress());
            statement.setString(5, entity.getEmail());
            statement.setInt(6, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Item with ID {} has been updated.\n", id);
                return entity;
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to update item");
        }

        return null;
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM purchase WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info("Item with ID {} has been deleted.\n", id);

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to delete item");
        }
    }

    @Override
    public Collection<Purchase> findAll() {
        Collection<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchase";
        logger.info("Reading all items.\n");

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Purchase purchase = mapResultSetToPurchase(resultSet);
                purchases.add(purchase);
            }

        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read items");
        }

        return purchases;
    }

    @Override
    public Purchase findByAddress(String address) {
        String query = "SELECT * FROM purchase WHERE address = ?";
        logger.info("Reading item with address {}\n", address);

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, address);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPurchase(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred", e);
            throw new RepositoryException("Failed to read item");
        }

        return null;
    }

    private Purchase mapResultSetToPurchase(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();
        purchase.setId(resultSet.getInt("id"));
        purchase.setAddress(resultSet.getString("address"));
        purchase.setQuantity(resultSet.getInt("quantity"));
        purchase.setName(resultSet.getString("name"));
        purchase.setEmail(resultSet.getString("email"));
        purchase.setHardwareId(resultSet.getInt("hardware_id"));
        return purchase;
    }
}
