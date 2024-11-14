package com.example.inventory.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.inventory.InventoryApplication;
import com.example.inventory.data.ItemsRepository;
import com.example.inventory.ui.home.HomeViewModel;
import com.example.inventory.ui.item.ItemDetailsViewModel;
import com.example.inventory.ui.item.ItemEditViewModel;
import com.example.inventory.ui.item.ItemEntryViewModel;

/**
 * Proveedor de fábrica para crear instancias de ViewModels con parámetros.
 */
public class AppViewModelProvider {

    public static class Factory implements ViewModelProvider.Factory {
        private final InventoryApplication application;
        private final int itemId;

        // Constructor para ViewModels que requieren un itemId
        public Factory(InventoryApplication application, int itemId) {
            this.application = application;
            this.itemId = itemId;
        }

        // Constructor para ViewModels que no requieren parámetros
        public Factory(InventoryApplication application) {
            this(application, -1);
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(HomeViewModel.class)) {
                return (T) new HomeViewModel((Application) application);
            } else if (modelClass.isAssignableFrom(ItemEntryViewModel.class)) {
                return (T) new ItemEntryViewModel(application);
            } else if (modelClass.isAssignableFrom(ItemDetailsViewModel.class)) {
                return (T) new ItemDetailsViewModel(application, itemId);
            } else if (modelClass.isAssignableFrom(ItemEditViewModel.class)) {
                return (T) new ItemEditViewModel(application, itemId);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
