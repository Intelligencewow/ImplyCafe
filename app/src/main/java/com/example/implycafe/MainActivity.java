package com.example.implycafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.onItemClickListener {
    ProductViewModel productViewModel;
    private final ActivityResultLauncher<Intent> addProductLauncher =
            registerForActivityResult(new ActivityResultContract<Intent, Product>() {

                @NonNull
                @Override
                public Intent createIntent(@NonNull Context context, Intent input) {
                    return input;
                }

                @Override
                public Product parseResult(int resultCode, @Nullable Intent intent) {
                    if (resultCode == RESULT_OK && intent != null) {
                        if (intent.hasExtra("editedProduct")) {
                            return (Product) intent.getSerializableExtra("editedProduct");
                        } else {
                            return (Product) intent.getSerializableExtra("newProduct");
                        }
                    }
                    return null;
                }

            }, new ActivityResultCallback<Product>() {
                @Override
                public void onActivityResult(Product product) {

                    if (product != null) {

                        productViewModel.checkIfProductExists(product.getId()).observe(MainActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean exists) {
                                if (exists) {
                                    productViewModel.updateProduct(product);
                                    Toast.makeText(MainActivity.this, "Produto atualizado!", Toast.LENGTH_SHORT).show();
                                } else {
                                    productViewModel.insert(product);
                                    Toast.makeText(MainActivity.this, "Produto adicionado!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(this, productList, this);
        recyclerView.setAdapter(productAdapter);


        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProductList().observe(this, products -> {
            productList = products;
            productAdapter.setProductList(products);
            Log.d("MainActivity", "Total de produtos: " + products.size());
        });

        FloatingActionButton createProduct = findViewById(R.id.createProduct);
        createProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            addProductLauncher.launch(intent);
        });

    }

    private AlertDialog createRemoveDialog(Product product, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover");
        builder.setMessage("Tem certeza de que deseja remover o produto da lista?");
        builder.setPositiveButton("SIM", (dialog, which) -> {
            productViewModel.delete(product);
            Toast.makeText(this, "Produto removido !", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }


    @Override
    public void onItemLongClick(int position) {
        Product product = productList.get(position);
        AlertDialog dialog = createRemoveDialog(product, position);
        dialog.show();
    }

    @Override

    public void onItemClick(int position) {
        Product product = productList.get(position);
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);

        intent.putExtra("productToEdit", product);
        intent.putExtra("position", position);
        intent.putExtra("productId", product.getId());

        addProductLauncher.launch(intent);

    }
}