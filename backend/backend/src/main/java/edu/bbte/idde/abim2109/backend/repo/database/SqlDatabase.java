package edu.bbte.idde.abim2109.backend.repo.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.abim2109.backend.config.ConfigurationFactory;
import edu.bbte.idde.abim2109.backend.config.JdbcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class SqlDatabase {

    private static final Logger logger = LoggerFactory.getLogger(SqlDatabase.class);

    private static SqlDatabase instance;

    private HikariDataSource dataSource;

    private SqlDatabase() {
        initializeDataSource();
    }

    public static synchronized SqlDatabase getInstance() {
        if (instance == null) {
            instance = new SqlDatabase();
        }
        return instance;
    }

    private void initializeDataSource() {
        JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(jdbcConfiguration.getDriverClassName());
        config.setJdbcUrl(jdbcConfiguration.getUrl());
        config.setUsername(jdbcConfiguration.getUserName());
        config.setPassword(jdbcConfiguration.getPassword());
        config.setMaximumPoolSize(jdbcConfiguration.getPoolSize());

        dataSource = new HikariDataSource(config);
        logger.info("HikariCP DataSource initialized.");
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("HikariCP DataSource closed.");
        }
    }
}
