package com.example.inventory.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemsRepository;
import com.example.inventory.data.OfflineItemsRepository;
import com.example.inventory.data.InventoryDatabase;

import android.app.Application;

import java.util.List;

/**
 * ViewModel para recuperar todos los ítems de la base de datos Room.
 */
public class HomeViewModel extends ViewModel {

    private final ItemsRepository itemsRepository;
    private final LiveData<HomeUiState> homeUiState;

    public HomeViewModel(Application application) {
        InventoryDatabase database = InventoryDatabase.getDatabase(application);
        itemsRepository = new OfflineItemsRepository(database.itemDao());
        LiveData<List<Item>> itemsLiveData = itemsRepository.getAllItemsStream();
        homeUiState = Transformations.map(itemsLiveData, HomeUiState::new);
    }

    public LiveData<HomeUiState> getHomeUiState() {
        return homeUiState;
    }
}
