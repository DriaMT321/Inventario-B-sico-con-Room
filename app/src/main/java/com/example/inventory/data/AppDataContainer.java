package com.example.inventory.data;

import android.content.Context;

/**
 * Implementación de AppContainer que proporciona una instancia de OfflineItemsRepository.
 */
public class AppDataContainer implements AppContainer {
    private final ItemsRepository itemsRepository;

    public AppDataContainer(Context context) {
        InventoryDatabase database = InventoryDatabase.getDatabase(context);
        itemsRepository = new OfflineItemsRepository(database.itemDao());
    }

    @Override
    public ItemsRepository getItemsRepository() {
        return itemsRepository;
    }
}
