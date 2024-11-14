package com.example.inventory.data;

/**
 * Contenedor de la aplicación para la inyección de dependencias.
 */
public interface AppContainer {
    ItemsRepository getItemsRepository();
}
