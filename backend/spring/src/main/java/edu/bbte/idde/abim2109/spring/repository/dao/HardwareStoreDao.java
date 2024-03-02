package edu.bbte.idde.abim2109.spring.repository.dao;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;

public interface HardwareStoreDao extends Dao<HardwareStore> {
    HardwareStore findByName(String name);
}
