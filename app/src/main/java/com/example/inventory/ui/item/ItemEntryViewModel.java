package com.example.inventory.ui.item;

import androidx.lifecycle.ViewModel;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemsRepository;
import com.example.inventory.data.InventoryDatabase;
import com.example.inventory.data.OfflineItemsRepository;

import android.app.Application;

public class ItemEntryViewModel extends ViewModel {

    private final ItemsRepository itemsRepository;

    public ItemEntryViewModel(Application application) {
        InventoryDatabase database = InventoryDatabase.getDatabase(application);
        itemsRepository = new OfflineItemsRepository(database.itemDao());
    }

    public void saveItem(String name, double price, int quantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemsRepository.insertItem(item);
    }
}
