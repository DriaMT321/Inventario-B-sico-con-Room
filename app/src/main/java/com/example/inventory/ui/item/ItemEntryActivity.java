package com.example.inventory.ui.item;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventory.InventoryApplication;
import com.example.inventory.R;
import com.example.inventory.ui.AppViewModelProvider;

public class ItemEntryActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private Button buttonSave;

    private ItemEntryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_entry);

        editTextName = findViewById(R.id.edit_text_item_name);
        editTextPrice = findViewById(R.id.edit_text_item_price);
        editTextQuantity = findViewById(R.id.edit_text_item_quantity);
        buttonSave = findViewById(R.id.button_save);

        viewModel = new ViewModelProvider(this, new AppViewModelProvider.Factory((InventoryApplication) getApplication()))
                .get(ItemEntryViewModel.class);

        buttonSave.setOnClickListener(view -> saveItem());
    }

    private void saveItem() {
        String name = editTextName.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, R.string.required_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.saveItem(name, price, quantity);
        finish();
    }
}
