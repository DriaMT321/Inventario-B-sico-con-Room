package com.example.inventory;

import android.app.Application;

import com.example.inventory.data.AppContainer;
import com.example.inventory.data.AppDataContainer;

/**
 * Clase de aplicación que inicializa el contenedor de dependencias.
 */
public class InventoryApplication extends Application {

    public AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();
        container = new AppDataContainer(this);
    }
}
