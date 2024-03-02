package edu.bbte.idde.abim2109.spring.service.dao;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.repository.dao.HardwareStoreDao;
import edu.bbte.idde.abim2109.spring.service.HardwareStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Profile("prod || dev")
@Service
public class HardwareStoreDaoService implements HardwareStoreService {
    @Autowired
    private HardwareStoreDao hardwareStoreDao;

    @Override
    public Collection<HardwareStore> getAllHardwareStores() {
        return hardwareStoreDao.findAll();
    }

    @Override
    public HardwareStore getHardwareStoreById(Long id) {
        return hardwareStoreDao.findById(id);
    }

    @Override
    public HardwareStore createHardwareStore(HardwareStore hardwareStore) {
        return hardwareStoreDao.create(hardwareStore);
    }

    @Override
    public HardwareStore updateHardwareStore(Long id, HardwareStore hardwareStore) {
        return hardwareStoreDao.update(id, hardwareStore);
    }

    @Override
    public void deleteHardwareStore(Long id) {
        hardwareStoreDao.delete(id);
    }
}
