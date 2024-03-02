package edu.bbte.idde.abim2109.backend.repo.factory;

import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;
import edu.bbte.idde.abim2109.backend.repo.PurchaseDao;
import edu.bbte.idde.abim2109.backend.repo.memdao.HardwareStoreMemDao;
import edu.bbte.idde.abim2109.backend.repo.memdao.PurchaseMemDao;

public class MemDaoFactory extends DaoFactory {
    private HardwareStoreDao hardwareStoreMemDao;
    private PurchaseDao purchaseMemDao;

    @Override
    public HardwareStoreDao getHardwareStoreDao() {
        if (hardwareStoreMemDao == null) {
            hardwareStoreMemDao = new HardwareStoreMemDao();
        }
        return hardwareStoreMemDao;
    }

    @Override
    public PurchaseDao getPurchaseDao() {
        if (purchaseMemDao == null) {
            purchaseMemDao = new PurchaseMemDao();
        }
        return purchaseMemDao;
    }
}
