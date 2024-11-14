package com.example.inventory.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa una fila en la base de datos.
 */
@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private double price;
    private int quantity;

    // Constructor vacío requerido por Room
    public Item() {
    }

    public Item(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    // Si necesitas establecer el ID (por ejemplo, en pruebas)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
