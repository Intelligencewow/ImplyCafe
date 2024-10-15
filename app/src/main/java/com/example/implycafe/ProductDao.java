package com.example.implycafe;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product WHERE id = :id")
    Product getProductById(int id);

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllProducts();

    @Delete
    void deleteProduct(Product product);


}
