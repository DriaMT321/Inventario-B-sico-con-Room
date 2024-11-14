package com.example.inventory.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Objeto de acceso a datos para acceder a la base de datos de inventario.
 */
@Dao
public interface ItemDao {

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM items WHERE id = :id")
    LiveData<Item> getItem(int id);

    // Estrategia de conflicto IGNORE para evitar insertar duplicados
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}
