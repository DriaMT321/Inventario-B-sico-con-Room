package com.example.inventory.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.R;
import com.example.inventory.InventoryApplication;
import com.example.inventory.data.Item;
import com.example.inventory.ui.AppViewModelProvider;
import com.example.inventory.ui.item.ItemDetailsActivity;
import com.example.inventory.ui.item.ItemEntryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        fabAddItem = findViewById(R.id.fab_add_item);
        recyclerView = findViewById(R.id.recycler_view_items);

        itemAdapter = new ItemAdapter(item -> {
            // Manejar el clic en un ítem
            Intent intent = new Intent(HomeActivity.this, ItemDetailsActivity.class);
            intent.putExtra("itemId", item.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        // Crear una instancia de la fábrica personalizada sin itemId
        AppViewModelProvider.Factory factory = new AppViewModelProvider.Factory(
                (InventoryApplication) getApplication());

        // Obtener el ViewModel utilizando la fábrica personalizada
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        // Observar el LiveData del ViewModel
        homeViewModel.getHomeUiState().observe(this, homeUiState -> {
            List<Item> items = homeUiState.getItemList();
            itemAdapter.setItemList(items);
        });

        // Configurar el listener para el botón "Add Item"
        fabAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ItemEntryActivity.class);
            startActivity(intent);
        });
    }
}
