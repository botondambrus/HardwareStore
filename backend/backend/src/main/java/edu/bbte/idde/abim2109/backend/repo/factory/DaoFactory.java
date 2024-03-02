package edu.bbte.idde.abim2109.backend.repo.factory;

import edu.bbte.idde.abim2109.backend.config.ConfigurationFactory;
import edu.bbte.idde.abim2109.backend.config.JdbcConfiguration;
import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;
import edu.bbte.idde.abim2109.backend.repo.PurchaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;
    private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);



    public static synchronized DaoFactory getInstance() {
        JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
        if (instance == null) {
            String daoType = jdbcConfiguration.getDaoType();
            if (daoType != null && daoType.equalsIgnoreCase("sql")) {
                logger.info("Using SQL DAO");
                instance = new SqlDaoFactory();
            } else {
                logger.info("Using MEM DAO");
                instance = new MemDaoFactory();
            }
        }

        return instance;
    }

    public abstract HardwareStoreDao getHardwareStoreDao();

    public abstract PurchaseDao getPurchaseDao();

}
