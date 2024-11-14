package com.example.inventory.ui.home;

import com.example.inventory.data.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Estado de la interfaz de usuario para la pantalla de inicio.
 */
public class HomeUiState {
    private final List<Item> itemList;

    public HomeUiState() {
        this.itemList = new ArrayList<>();
    }

    public HomeUiState(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
