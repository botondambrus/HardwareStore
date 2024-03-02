package edu.bbte.idde.abim2109.spring.repository.jpa;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface HardwareStoreSpringDataDao extends JpaRepository<HardwareStore, Long>,
        JpaSpecificationExecutor<HardwareStore> {
    HardwareStore findByName(String name);
}
