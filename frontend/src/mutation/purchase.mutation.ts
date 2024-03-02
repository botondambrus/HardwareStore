import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createPurchase, deletePurchase, updatePurchase } from "../api/purchase.api";
import { CreatePurchase } from "../models/Purchase";
import { dbPromise } from "../services/db";

export const useDeletePurchase = (id: number) => {
    const queryClient = useQueryClient();
  
    return useMutation({
      mutationFn: (purchaseId: number) => deletePurchase(id, purchaseId),
      onSuccess: async (data: number) => {
        const db = await dbPromise;
        db.delete('purchases', data);
        queryClient.invalidateQueries({ queryKey: ['purchase', id] });
      },
    });
  };
  
  export const useCreatePurchase = (id: number) => {
    const queryClient = useQueryClient();
  
    return useMutation({
      mutationFn: (data: CreatePurchase) => createPurchase(id, data),
      onSuccess: async (data) => {
        const db = await dbPromise;
        console.log(data);
        db.put('purchases', data, data.id);
        queryClient.invalidateQueries({ queryKey: ['purchase', id] });
      },
    });
  }
  
  export const useUpdatePurchase = (id: number, purchaseId: number) => {
    const queryClient = useQueryClient();
  
    return useMutation({
      mutationFn: (data: CreatePurchase) => updatePurchase(id, purchaseId, data),
      onSuccess: async (data) => {
        const db = await dbPromise;
        db.put('purchases', data, data.id);
        queryClient.invalidateQueries({ queryKey: ['purchase', id] });
      },
    });
  }
  
