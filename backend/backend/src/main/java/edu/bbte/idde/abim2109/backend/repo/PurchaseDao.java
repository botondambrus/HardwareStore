package edu.bbte.idde.abim2109.backend.repo;

import edu.bbte.idde.abim2109.backend.model.Purchase;

public interface PurchaseDao extends Dao<Purchase> {
    Purchase findByAddress(String name);
}
