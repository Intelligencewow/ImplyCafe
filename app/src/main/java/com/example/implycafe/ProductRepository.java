package com.example.implycafe;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDao productDao;
    private ExecutorService executor;

    public ProductRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        productDao = db.productDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insert(final Product product){

        executor.execute(() -> productDao.insertProduct(product));
    }

    public void delete(final Product product){
        executor.execute(() -> productDao.deleteProduct(product));
    }

    public LiveData<Boolean> checkIfProductExists(String id){
        MutableLiveData<Boolean> existsLiveData = new MutableLiveData<Boolean>();
        executor.execute(() -> {
            int count = productDao.checkIfProductExists(id);
            existsLiveData.postValue(count > 0);
        });

        return existsLiveData;

    }

    public LiveData<List<Product>> getAllProducts(){
        return productDao.getAllProducts();
    }

    public void updateProduct(Product product){
        executor.execute(() -> productDao.updateProduct(product));
    }
}
