package edu.bbte.idde.abim2109.backend.repo.factory;

import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;
import edu.bbte.idde.abim2109.backend.repo.PurchaseDao;
import edu.bbte.idde.abim2109.backend.repo.database.SqlDatabase;
import edu.bbte.idde.abim2109.backend.repo.sqldao.HardwareStoreSqlDao;
import edu.bbte.idde.abim2109.backend.repo.sqldao.PurchaseSqlDao;

public class SqlDaoFactory extends DaoFactory {
    private final SqlDatabase database = SqlDatabase.getInstance();
    private HardwareStoreDao hardwareStoreSqlDao;
    private PurchaseDao purchaseSqlDao;

    @Override
    public HardwareStoreDao getHardwareStoreDao() {
        if (hardwareStoreSqlDao == null) {
            hardwareStoreSqlDao = new HardwareStoreSqlDao(database);
        }
        return hardwareStoreSqlDao;
    }

    @Override
    public PurchaseDao getPurchaseDao() {
        if (purchaseSqlDao == null) {
            purchaseSqlDao = new PurchaseSqlDao(database);
        }
        return purchaseSqlDao;
    }
}
