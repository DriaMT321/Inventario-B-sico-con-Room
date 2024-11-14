package com.example.inventory.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemsRepository;
import com.example.inventory.data.OfflineItemsRepository;
import com.example.inventory.data.InventoryDatabase;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ViewModel para agregar y editar ítems.
 */
public class ItemEditViewModel extends ViewModel {

    private final ItemsRepository itemsRepository;
    private final MutableLiveData<Item> itemLiveData = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ItemEditViewModel(Application application, int itemId) {
        InventoryDatabase database = InventoryDatabase.getDatabase(application);
        itemsRepository = new OfflineItemsRepository(database.itemDao());

        if (itemId != -1) {
            itemsRepository.getItemStream(itemId).observeForever(item -> {
                if (item != null) {
                    itemLiveData.setValue(item);
                }
            });
        }
    }

    public LiveData<Item> getItem() {
        return itemLiveData;
    }

    public void saveItem(String name, double price, int quantity) {
        Item item = itemLiveData.getValue();
        if (item != null) {
            item.setName(name);
            item.setPrice(price);
            item.setQuantity(quantity);
            executorService.execute(() -> itemsRepository.updateItem(item));
        } else {
            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setQuantity(quantity);
            executorService.execute(() -> itemsRepository.insertItem(newItem));
        }
    }
}
