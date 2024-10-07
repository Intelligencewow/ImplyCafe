package com.example.implycafe;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {
    private final String id;
    String name;
    String description;
    String price;
    String imageUrl;

    public Product(String name, String description, String price){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = "";
    }

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
