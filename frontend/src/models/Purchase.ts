export type Purchase = {
    id: number;
    name: string;
    address: string;
    email: string;
    quantity: number;
    date: number;
    hardwareStoreId: number;
}

export type CreatePurchase = {
    name: string;
    address: string;
    email: string;
    quantity: number;
}
