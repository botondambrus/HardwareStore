package edu.bbte.idde.abim2109.spring.service;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;

import java.util.Collection;

public interface HardwareStoreService {

    Collection<HardwareStore> getAllHardwareStores();

    HardwareStore getHardwareStoreById(Long id);

    HardwareStore createHardwareStore(HardwareStore hardwareStore);

    HardwareStore updateHardwareStore(Long id, HardwareStore hardwareStore);

    void deleteHardwareStore(Long id);
}
