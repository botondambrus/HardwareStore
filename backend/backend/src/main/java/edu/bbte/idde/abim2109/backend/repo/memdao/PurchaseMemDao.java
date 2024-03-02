package edu.bbte.idde.abim2109.backend.repo.memdao;

import edu.bbte.idde.abim2109.backend.repo.PurchaseDao;
import edu.bbte.idde.abim2109.backend.model.Purchase;

import java.util.Locale;

public class PurchaseMemDao extends AbstractMemDao<Purchase> implements PurchaseDao {

    @Override
    public Purchase findByAddress(String address) {
        return storage.values().stream()
                .filter(purchase ->
                        purchase.getAddress().toLowerCase(Locale.ROOT).contains(address.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(null);
    }
}
