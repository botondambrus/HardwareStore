import { useQuery } from '@tanstack/react-query';
import { getPurchase, getPurchases } from '../api/purchase.api';
import { dbPromise } from '../services/db';
import { Purchase } from '../models/Purchase';

export const usePurchases = (id: number) => {
  return useQuery({
    queryKey: ['purchase', id],
    queryFn: async () => {
      try {
        const data = await getPurchases(id);
        const db = await dbPromise;
        data.forEach(item => {
          db.put('purchases', item, item.id);
        });
        return data;
      } catch (error) {
        const db = await dbPromise;
        const purchases = await db.getAll('purchases');
        return purchases.filter((purchase: Purchase) => purchase.hardwareStoreId === id);
      }
    }
  });
};

export const usePurchase = (id: number, purchaseId: number) => {
  return useQuery({
    queryKey: ['purchase', id, purchaseId],
    queryFn: async () => {
      try {
        const data = await getPurchase(id, purchaseId);
        const db = await dbPromise;
        db.put('purchases', data, purchaseId);
        return data;
      } catch (error) {
        const db = await dbPromise;
        return db.get('purchases', purchaseId);
      }
    }
  });
}
