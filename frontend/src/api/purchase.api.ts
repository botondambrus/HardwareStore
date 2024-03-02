import { CreatePurchase, Purchase } from '../models/Purchase';
import { hardwareStoreApi } from './common.api';

export const getPurchases = async (id: number): Promise<Purchase[]> => {
  const response = await hardwareStoreApi.get<Purchase[]>(`/hardware-stores/${id}/purchases`);
  return response.data;
};

export const getPurchase = async (id: number, purchaseId: number): Promise<Purchase> => {
  const response = await hardwareStoreApi.get<Purchase>(`/hardware-stores/${id}/purchases/${purchaseId}`);
  return response.data;
};

export const deletePurchase = async (id: number, purchaseId: number): Promise<number> => {
  const response = await hardwareStoreApi.delete(`/hardware-stores/${id}/purchases/${purchaseId}`);
  return response.data;
};

export const createPurchase = async (id: number, data: CreatePurchase): Promise<Purchase> => {
  const returnedData = await hardwareStoreApi.post(`/hardware-stores/${id}/purchases`, data);
  return returnedData.data;
};

export const updatePurchase = async (id: number, purchaseId: number, data: CreatePurchase): Promise<Purchase> => {
  const returnedData = await hardwareStoreApi.put(`/hardware-stores/${id}/purchases/${purchaseId}`, data);
  return returnedData.data;
};
