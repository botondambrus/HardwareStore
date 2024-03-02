import { openDB } from 'idb';

export const dbPromise = openDB('hardwareStore', 1, {
  upgrade(db) {
    db.createObjectStore('items');
    db.createObjectStore('purchases');
  },
});
