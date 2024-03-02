package edu.bbte.idde.abim2109.spring.service.scheduledtask;

import edu.bbte.idde.abim2109.spring.model.HardwareStore;
import edu.bbte.idde.abim2109.spring.model.Purchase;
import edu.bbte.idde.abim2109.spring.service.jpa.HardwareStoreJpaService;
import edu.bbte.idde.abim2109.spring.service.jpa.PurchaseJpaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Transactional
public class ScheduledTasks {
    @Autowired
    private HardwareStoreJpaService hardwareStoreJpaService;

    @Autowired
    private PurchaseJpaService purchaseJpaService;

    @Scheduled(cron = "0 46 12 * * ?")
    public void generateHardwareStoresStatistics() {

        List<HardwareStore> allHardwareStores = hardwareStoreJpaService.getAllHardwareStores();
        int totalQuantity = allHardwareStores.stream().mapToInt(HardwareStore::getQuantity).sum();
        double averagePrice = allHardwareStores.stream().mapToInt(HardwareStore::getPrice).average().orElse(0);

        log.info("Statistics generated:"
                + "\n" + "Total quantity: "
                + totalQuantity + "\n"
                + "Average price: " + averagePrice);
    }

    @Scheduled(cron = "0 53 12 * * ?")
    public void generatePurchasesStatistics() {

        int totalPurchases = purchaseJpaService.getAllPurchases().size();
        double totalPurchasesPrice = purchaseJpaService.getAllPurchases().stream()
                        .mapToDouble(purchase -> purchase.getQuantity() * purchase.getHardwareStore().getPrice()).sum();

        log.info("Statistics generated:"
                + "\n" + "Total purchases: "
                + totalPurchases + "\n"
                + "Total purchases price: " + totalPurchasesPrice);
    }

    @Scheduled(cron = "0 53 12 * * ?")
    public void deleteOldPurchases() {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();

        List<Purchase> allPurchases = purchaseJpaService.getAllPurchases();

        for (Purchase purchase : allPurchases) {
            if (purchase.getDate().before(oneYearAgo)) {
                HardwareStore hardwareStore = purchase.getHardwareStore();
                hardwareStore.getPurchases().remove(purchase);
                hardwareStoreJpaService.saveHardwareStore(hardwareStore);
            }
        }
        log.info("Old purchases deleted");
    }
}