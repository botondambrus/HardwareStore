export type HardwareStoreItem = {
  id: number;
  name: string;
  price: number;
  description: string;
  quantity: number;
  category: string;
};

export type CreateHardwareStoreItem = {
  name: string;
  price: number;
  description: string;
  quantity: number;
  category: string;
};
