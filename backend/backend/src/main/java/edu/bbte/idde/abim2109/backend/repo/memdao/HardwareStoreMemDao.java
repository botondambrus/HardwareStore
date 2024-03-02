package edu.bbte.idde.abim2109.backend.repo.memdao;

import edu.bbte.idde.abim2109.backend.model.HardwareStore;
import edu.bbte.idde.abim2109.backend.repo.HardwareStoreDao;

import java.util.Locale;

public class HardwareStoreMemDao extends AbstractMemDao<HardwareStore> implements HardwareStoreDao {
    public HardwareStoreMemDao() {
        super();
        HardwareStore hardwareStore1 = new HardwareStore(
                "Nvidia RTX 3080", "Graphics Cards", "A graphics card", 1000, 10);
        this.create(hardwareStore1);

        HardwareStore hardwareStore2 = new HardwareStore(
                "Intel i7 10700K", "Processors", "A processor", 700, 20);
        this.create(hardwareStore2);

        HardwareStore hardwareStore3 = new HardwareStore(
                "Corsair Vengeance", "Memory", "A memory kit", 300, 100);
        this.create(hardwareStore3);

        HardwareStore hardwareStore4 = new HardwareStore(
                "Intel Core I9 10900K", "Processors", "A processor", 1200, 50);
        this.create(hardwareStore4);
    }

    @Override
    public HardwareStore findByName(String name) {
        return storage.values().stream()
                .filter(hardwareStore ->
                        hardwareStore.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(null);
    }

}
