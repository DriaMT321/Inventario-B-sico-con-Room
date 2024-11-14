package com.example.inventory.ui.item;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.InventoryApplication;
import com.example.inventory.R;
import com.example.inventory.data.Item;
import com.example.inventory.ui.AppViewModelProvider;

import java.text.NumberFormat;
import java.util.Locale;

public class ItemDetailsActivity extends AppCompatActivity {

    private ItemDetailsViewModel viewModel;
    private TextView textViewName;
    private TextView textViewPrice;
    private TextView textViewQuantity;
    private Button buttonSell;
    private Button buttonDelete; // Referencia al nuevo botón de eliminar
    private int itemId;
    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        textViewName = findViewById(R.id.text_view_item_name);
        textViewPrice = findViewById(R.id.text_view_item_price);
        textViewQuantity = findViewById(R.id.text_view_item_quantity);
        buttonSell = findViewById(R.id.button_sell);
        buttonDelete = findViewById(R.id.button_delete); // Inicialización del botón de eliminar

        itemId = getIntent().getIntExtra("itemId", -1);
        if (itemId == -1) {
            // Manejar el caso donde no se pasó un itemId válido
            Toast.makeText(this, R.string.invalid_item, Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay un itemId válido
            return;
        }

        // Crear una instancia de la fábrica personalizada con el itemId
        AppViewModelProvider.Factory factory = new AppViewModelProvider.Factory(
                (InventoryApplication) getApplication(), itemId);

        // Obtener el ViewModel utilizando la fábrica personalizada
        viewModel = new ViewModelProvider(this, factory).get(ItemDetailsViewModel.class);

        // Observar el LiveData del ViewModel
        viewModel.getUiState().observe(this, item -> {
            if (item != null) {
                currentItem = item;
                updateUI(currentItem);
            }
        });

        // Configurar el listener para el botón "Sell"
        buttonSell.setOnClickListener(view -> {
            if (currentItem != null && currentItem.getQuantity() > 0) {
                viewModel.reduceQuantityByOne();
                Toast.makeText(this, R.string.sell_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.out_of_stock, Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el listener para el botón "Eliminar Producto"
        buttonDelete.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void updateUI(Item item) {
        textViewName.setText(item.getName());
        textViewPrice.setText(formatPrice(item.getPrice()));
        textViewQuantity.setText(getString(R.string.in_stock, item.getQuantity()));
        buttonSell.setEnabled(item.getQuantity() > 0);
    }

    private String formatPrice(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return format.format(price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itemMenu) {
        int id = itemMenu.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(ItemDetailsActivity.this, ItemEditActivity.class);
            intent.putExtra("itemId", itemId);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete) {
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(itemMenu);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.attention)
                .setMessage(R.string.delete_question)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    viewModel.deleteItem();
                    Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
