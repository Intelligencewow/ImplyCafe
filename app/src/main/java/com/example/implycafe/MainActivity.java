package com.example.implycafe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.onItemClickListener{
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private AlertDialog.Builder builder;



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
                        if (intent.hasExtra("editedProduct")){
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

                    if (product != null){

                        boolean exists = false;
                        int position = -1;

                        for (int i = 0; i < productList.size(); i++){
                            if (productList.get(i).getId().equals(product.getId())){
                                exists = true;
                                position = i;
                                break;
                            }
                        }
                        if (exists){
                            productList.set(position, product);
                            productAdapter.notifyItemChanged(position);
                            Toast.makeText(MainActivity.this, "Produto atualizado!", Toast.LENGTH_SHORT);
                        } else {
                            productList.add(product);
                            productAdapter.notifyItemInserted(productList.size() - 1);
                            Toast.makeText(MainActivity.this,"Produto adicionado!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


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

        productList = new ArrayList<>();
        productList.add(new Product("Coca cola", "ela tá gelada", "10"));
        productList.add(new Product("Pepsi", "ela tá gelada", "7"));
        productList.add(new Product("Sprite", "ela tá gelada", "4.50"));
        productList.add(new Product("Dolly", "ela tá gelada", "12.99"));
        productList.add(new Product("Sprite", "ela tá gelada", "4.50"));
        productList.add(new Product("Dolly", "ela tá gelada", "12.99"));

        productList.add(new Product("Sprite", "ela tá gelada", "4.50"));
        productList.add(new Product("Dolly", "ela tá gelada", "12.99"));


        productAdapter = new ProductAdapter(this, productList, this);
        recyclerView.setAdapter(productAdapter);

        FloatingActionButton createProduct = findViewById(R.id.createProduct);
        createProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ProductActivity.class);
            addProductLauncher.launch(intent);
        });

    }

    private AlertDialog createRemoveDialog(Product product, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover");
        builder.setMessage("Tem certeza de que deseja remover o produto da lista?");
        builder.setPositiveButton("SIM", (dialog, which) -> {
            productList.remove(position);
            productAdapter.notifyItemRemoved(position);
            productAdapter.notifyItemRangeChanged(position, productList.size());
            Toast.makeText(this,"Produto removido !", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }


    @Override
    public void onItemLongClick(int position) {
        Product product = productList.get(position);
        AlertDialog dialog = createRemoveDialog(product,position);
        dialog.show();
    }

    @Override

    public void onItemClick(int position) {
        Product product = productList.get(position);
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);

        intent.putExtra("productToEdit", product);
        intent.putExtra("position", position);

        addProductLauncher.launch(intent);

    }
}