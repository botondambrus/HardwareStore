package edu.bbte.idde.abim2109.spring.service.jpa;

import edu.bbte.idde.abim2109.spring.model.Purchase;
import edu.bbte.idde.abim2109.spring.model.filter.PurchaseFilter;
import edu.bbte.idde.abim2109.spring.repository.jpa.PurchaseSpringDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("jpa")
@Service
public class PurchaseJpaService {
    @Autowired
    private PurchaseSpringDataDao purchaseSpringDataDao;

    public Page<Purchase> getAllPurchasesByHardwareStoreId(
            Long hardwareStoreId,
            Pageable pageable,
            PurchaseFilter filter) {
        return purchaseSpringDataDao.findAll(
                Specification.where(specification.PurchaseSpecification.quantityGreaterThanOrEq(filter.getMinQuantity())
                        .and(specification.PurchaseSpecification.quantityLessThanOrEq(filter.getMaxQuantity()))
                        .and(specification.PurchaseSpecification.nameEqual(filter.getName()))
                        .and(specification.PurchaseSpecification.hardwareStoreIdEqual(hardwareStoreId))),
                pageable);

    }

    public List<Purchase> getAllPurchases() {
        return purchaseSpringDataDao.findAll();
    }
}
