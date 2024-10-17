package com.example.implycafe;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;


@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id;
    @ColumnInfo(name = "nome")
    public String name;
    @ColumnInfo(name = "descricao")
    public String description;
    @ColumnInfo(name = "preco")
    public String price;
    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    public Product(String name, String description, String price, String imageUrl){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String url) { this.imageUrl = url; }

}
