package com.example.implycafe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDao productDao;

    public ProductRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        productDao = db.productDao();
    }

    public void insert(final Product product){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> productDao.insertProduct(product));
    }

    public LiveData<List<Product>> getAllProducts(){
        return productDao.getAllProducts();
    }
}
