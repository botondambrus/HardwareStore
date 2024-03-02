import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteHardwareStoreItem, createHardwareStoreItem, updateHardwareStoreItem } from '../api/hardwarestore.api';
import { CreateHardwareStoreItem } from '../models/HardwareStore';
import { dbPromise } from '../services/db';

export const useDeleteHardwareStoreItem = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteHardwareStoreItem,
    onSuccess: async (id) => {
      const db = await dbPromise;
      db.delete('items', id);
      queryClient.invalidateQueries({ queryKey: ['hardwareStore'] });
    },
  });
};

export const useCreateHardwareStoreItem = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createHardwareStoreItem,
    onSuccess: async (data) => {
      const db = await dbPromise;
      db.put('items', data, data.id);
      queryClient.invalidateQueries({ queryKey: ['hardwareStore'] });
    },
  });
}

export const useUpdateHardwareStoreItem = (id: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: CreateHardwareStoreItem) => updateHardwareStoreItem(id, data),
    onSuccess: async (data) => {
      const db = await dbPromise;
      db.put('items', data, data.id);
      queryClient.invalidateQueries({ queryKey: ['hardwareStore'] });
    },
  });
}