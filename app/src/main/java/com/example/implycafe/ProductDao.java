package com.example.implycafe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product WHERE id = :id")
    int getProductById(String id);

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllProducts();

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT COUNT(*) FROM product WHERE id = :id")
    int checkIfProductExists(String id);

    @Update
    void updateProduct(Product product);


}
