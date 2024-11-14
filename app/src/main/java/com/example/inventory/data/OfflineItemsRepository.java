package com.example.inventory.data;

import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * Implementación del repositorio que interactúa con la base de datos local.
 */
public class OfflineItemsRepository implements ItemsRepository {

    private final ItemDao itemDao;

    public OfflineItemsRepository(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public LiveData<List<Item>> getAllItemsStream() {
        return itemDao.getAllItems();
    }

    @Override
    public LiveData<Item> getItemStream(int id) {
        return itemDao.getItem(id);
    }

    @Override
    public void insertItem(Item item) {
        InventoryDatabase.databaseWriteExecutor.execute(() -> itemDao.insert(item));
    }

    @Override
    public void deleteItem(Item item) {
        InventoryDatabase.databaseWriteExecutor.execute(() -> itemDao.delete(item));
    }

    @Override
    public void updateItem(Item item) {
        InventoryDatabase.databaseWriteExecutor.execute(() -> itemDao.update(item));
    }
}
