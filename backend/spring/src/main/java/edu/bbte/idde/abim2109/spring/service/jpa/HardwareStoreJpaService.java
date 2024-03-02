package edu.bbte.idde.abim2109.spring.service.jpa;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.model.filter.HardwareStoreFilter;
import edu.bbte.idde.abim2109.spring.repository.jpa.HardwareStoreSpringDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import specification.HardwareStoreSpecification;

import java.util.List;

@Profile("jpa")
@Service
public class HardwareStoreJpaService {
    @Autowired
    private HardwareStoreSpringDataDao hardwareStoreSpringDataDao;

    public List<HardwareStore> getAllHardwareStores() {
        return hardwareStoreSpringDataDao.findAll();
    }

    @Cacheable("hardwareStores")
    public Page<HardwareStore> getAllHardwareStores(Pageable pageable, HardwareStoreFilter filter) {
        return hardwareStoreSpringDataDao.findAll(
                Specification.where(HardwareStoreSpecification.priceGreaterThanOrEq(filter.getMinPrice())
                        .and(HardwareStoreSpecification.priceLessThanOrEq(filter.getMaxPrice()))
                        .and(HardwareStoreSpecification.categoryEqual(filter.getCategory()))
                        .and(HardwareStoreSpecification.nameEqual(filter.getName()))
                ),
                pageable
        );
    }

    @Cacheable(value = "hardwareStore", key = "#id")
    public HardwareStore getHardwareStoreById(Long id) {
        return hardwareStoreSpringDataDao.findById(id).orElse(null);
    }

    @CacheEvict(value = {"hardwareStores", "hardwareStore"}, allEntries = true)
    public HardwareStore saveHardwareStore(HardwareStore hardwareStore) {
        return hardwareStoreSpringDataDao.save(hardwareStore);
    }

    @CacheEvict(value = {"hardwareStores", "hardwareStore"}, allEntries = true)
    public void deleteHardwareStore(Long id) {
        hardwareStoreSpringDataDao.deleteById(id);
    }
}
