package com.example.inventory.ui.item;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemsRepository;
import com.example.inventory.data.OfflineItemsRepository;
import com.example.inventory.data.InventoryDatabase;

import android.app.Application;

public class ItemDetailsViewModel extends ViewModel {

    private final ItemsRepository itemsRepository;
    private LiveData<Item> itemLiveData;
    private int itemId;

    public ItemDetailsViewModel(Application application, int itemId) {
        InventoryDatabase database = InventoryDatabase.getDatabase(application);
        itemsRepository = new OfflineItemsRepository(database.itemDao());
        this.itemId = itemId;
        itemLiveData = itemsRepository.getItemStream(itemId);
    }

    public LiveData<Item> getUiState() {
        return itemLiveData;
    }

    public void reduceQuantityByOne() {
        Item item = itemLiveData.getValue();
        if (item != null && item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            itemsRepository.updateItem(item);
        }
    }

    public void deleteItem() {
        Item item = itemLiveData.getValue();
        if (item != null) {
            itemsRepository.deleteItem(item);
        }
    }

    /**
     * Factory para crear una instancia de ItemDetailsViewModel con parámetros.
     */
    public static class Factory implements ViewModelProvider.Factory {
        private final Application application;
        private final int itemId;

        public Factory(Application application, int itemId) {
            this.application = application;
            this.itemId = itemId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ItemDetailsViewModel.class)) {
                return (T) new ItemDetailsViewModel(application, itemId);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
