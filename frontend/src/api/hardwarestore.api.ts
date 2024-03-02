import { CreateHardwareStoreItem, HardwareStoreItem } from '../models/HardwareStore';
import { HardwareStoreFilter } from '../models/HardwareStoreFilter';
import { hardwareStoreApi } from './common.api';

export const getHardwareStoreItems = async (filter: HardwareStoreFilter): Promise<HardwareStoreItem[]> => {
  const response = await hardwareStoreApi.get<HardwareStoreItem[]>('/hardware-stores', { params: filter });
  return response.data;
}

export const getHardwareStoreItem = async (id: number): Promise<HardwareStoreItem> => {
  const response = await hardwareStoreApi.get<HardwareStoreItem>(`/hardware-stores/${id}`);
  return response.data;
};

export const deleteHardwareStoreItem = async (data: number): Promise<number> => {
  const returnedData = await hardwareStoreApi.delete(`/hardware-stores/${data}`);
  return returnedData.data;
};

export const createHardwareStoreItem = async (data: CreateHardwareStoreItem): Promise<HardwareStoreItem> => {
  const returnedData = await hardwareStoreApi.post('/hardware-stores', data);
  return returnedData.data;
};

export const updateHardwareStoreItem = async (id: number, data: CreateHardwareStoreItem): Promise<HardwareStoreItem> => {
  const returnedData = await hardwareStoreApi.put(`/hardware-stores/${id}`, data);
  return returnedData.data;
};
