export type HardwareStoreFilter = {
    [key: string]: string | number | undefined;
    category?: string;
    minPrice?: number;
    maxPrice?: number;
    name?: string;
};