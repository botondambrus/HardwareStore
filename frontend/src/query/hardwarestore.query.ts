import { useQuery } from "@tanstack/react-query";
import { getHardwareStoreItem, getHardwareStoreItems } from "../api/hardwarestore.api";
import { HardwareStoreFilter } from "../models/HardwareStoreFilter";
import { dbPromise } from "../services/db";

export const useHardwareStoreItems = (filter: HardwareStoreFilter) => {
  return useQuery({
    queryKey: ['hardwareStore'],
    queryFn: async () => {
      try {
        const data = await getHardwareStoreItems(filter);
        const db = await dbPromise;
        data.forEach(item => {
          db.put('items', item, item.id);
        });
        return data;
      } catch (error) {
        const db = await dbPromise;
        return db.getAll('items');
      }
    }
  });
};

export const useHardwareStoreItem = (id: number) => {
  return useQuery({
    queryKey: ['hardwareStore', id],
    queryFn: async () => {
      try {
        const data = await getHardwareStoreItem(id);
        const db = await dbPromise;
        db.put('items', data, id);
        return data;
      } catch (error) {
        const db = await dbPromise;
        return db.get('items', id);
      }
    }
  });
}