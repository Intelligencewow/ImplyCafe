package com.example.implycafe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository productRepository;
    private LiveData<List<Product>> productList;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        productList = productRepository.getAllProducts();
    }

    public void insert(Product product){
        productRepository.insert(product);
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

    public LiveData<Boolean> checkIfProductExists(String id){
        return productRepository.checkIfProductExists(id);
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public void updateProduct(Product product){
        productRepository.updateProduct(product);
    }
}
