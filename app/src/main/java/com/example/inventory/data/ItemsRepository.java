package com.example.inventory.data;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repositorio que proporciona métodos para insertar, actualizar, eliminar y recuperar ítems.
 */
public interface ItemsRepository {
    /**
     * Recupera todos los ítems del origen de datos.
     */
    LiveData<List<Item>> getAllItemsStream();

    /**
     * Recupera un ítem que coincide con el ID dado.
     */
    LiveData<Item> getItemStream(int id);

    /**
     * Inserta un ítem en el origen de datos.
     */
    void insertItem(Item item);

    /**
     * Elimina un ítem del origen de datos.
     */
    void deleteItem(Item item);

    /**
     * Actualiza un ítem en el origen de datos.
     */
    void updateItem(Item item);
}
