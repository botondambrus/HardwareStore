package edu.bbte.idde.abim2109.backend.repo;

import edu.bbte.idde.abim2109.backend.model.HardwareStore;

public interface HardwareStoreDao extends Dao<HardwareStore> {
    HardwareStore findByName(String name);
}
