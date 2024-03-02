package edu.bbte.idde.abim2109.spring.repository.dao.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.abim2109.spring.configuration.DatabaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Profile("prod")
public class SqlDatabase {
    @Autowired
    private DatabaseConfiguration databaseConfiguration;
    private HikariDataSource dataSource;

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseConfiguration.getUrl());
        config.setUsername(databaseConfiguration.getUser());
        config.setPassword(databaseConfiguration.getPassword());
        config.setDriverClassName(databaseConfiguration.getDriverClassName());
        config.setMaximumPoolSize(databaseConfiguration.getPoolSize());

        dataSource = new HikariDataSource(config);
        log.info("HikariCP DataSource initialized.");
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initializeDataSource();
        }

        return dataSource.getConnection();
    }

    public void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("HikariCP DataSource closed.");
        }
    }
}
